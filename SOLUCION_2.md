# Solución — Problema #2: Tienda Virtual

## Información General

| Campo | Detalle |
|-------|---------|
| **IA utilizada** | Claude Sonnet 4.6 (Anthropic) |
| **Tiempo invertido** | ~25 minutos |
| **Modalidad** | 100% asistido por IA generativa |

---

## Prompts Utilizados

### Prompt 1
> "dame contexto de los problemas y explicame tu estrategia de solucion para cada uno de los problemas"

**Resultado:** La IA exploró el repositorio completo, leyó todos los archivos del paquete `util`, analizó los diagramas UML y entregó un inventario de los 5 errores del proyecto con estrategias de corrección para cada uno.

---

### Prompt 2
> "ahora dame el analisis del problema dos, que esta relacionado con los codigos que estan en la carpeta util. dame tu estrategia solucion"

**Resultado:** La IA produjo un análisis detallado separando errores de compilación, errores de lógica y problemas de diseño. Identificó los dos patrones presentes (Abstract Factory + Observer), señaló la clase faltante (`PaymentFactory`), el import incorrecto, el bug del constructor y las dos asignaciones faltantes. Propuso la estrategia exacta de corrección para cada punto.

---

### Prompt 3
> "si por favor"

**Resultado:** La IA aplicó las 6 correcciones sobre los archivos existentes, compiló el proyecto completo (`BUILD SUCCESS`) y ejecutó los tests (`1/1 pasando`).

---

## Patrones de Diseño Identificados

### Abstract Factory
Permite crear familias de objetos relacionados (método de pago + su validación) sin exponer los detalles a la lógica principal de compras.

```
PaymentFactory <<interface>>
    createPaymentMethod(amount, customerId, description): PaymentMethod
         │
         ├── CreditCardFactory  →  crea pagos con tarjeta de crédito
         ├── PaypalFactory      →  crea pagos con PayPal
         └── CryptoFactory      →  crea pagos con criptomonedas
```

Cada factory está pre-configurada con los datos del cliente (número de tarjeta, email, wallet). Al llamar `createPaymentMethod()` produce una nueva instancia con el monto y descripción dinámicos.

### Observer
Permite notificar automáticamente a múltiples módulos cuando ocurre un evento de pago, sin que el core de pagos conozca a los suscriptores.

```
ECIPayment (sujeto)
    addObserver() / removeObserver() / notifyPaymentSuccess() / notifyPaymentFailed()
         │
         └── PaymentEventObserver (observador concreto)
                  ├── Inventory.discountProduct()     → descuenta stock
                  ├── Facturation.generateInvoice()   → genera factura
                  └── Notification.sendConfirmationEmail() → notifica al cliente
```

---

## Errores Identificados y Correcciones

### Error 1 — Interface `PaymentFactory` faltante

**Archivo:** `ECIPayment.java:21` — referencia a tipo inexistente.

```java
// ANTES: no compilaba
public boolean processPayment(PaymentFactory factory, ...) // PaymentFactory: símbolo no encontrado

// CORRECCIÓN: nuevo archivo PaymentFactory.java
public interface PaymentFactory {
    PaymentMethod createPaymentMethod(double amount, String customerId, String description);
}
```

Además se agregó `implements PaymentFactory` a las tres clases factory con su override correspondiente. Ejemplo en `CreditCardFactory`:

```java
@Override
public PaymentMethod createPaymentMethod(double amount, String customerId, String description) {
    return new CreditCardFactory(amount, customerId, description, number, name, expirationDate, cvv, address);
}
```

---

### Error 2 — Import incorrecto en `PaymentEventObserver.java`

```java
// ANTES: importaba clase JMX del JDK
import javax.management.Notification;

// CORRECCIÓN: import eliminado — Java resuelve la clase local del mismo paquete automáticamente
```

---

### Error 3 — Bug en constructor de `PaymentMethod.java`

```java
// ANTES: parámetro mal nombrado → customerID siempre null
public PaymentMethod(double amount, String transactionID, String description) {
    this.customerID = customerID; // usa el campo vacío, no el parámetro

// CORRECCIÓN
public PaymentMethod(double amount, String customerId, String description) {
    this.customerID = customerId;
```

---

### Error 4 — Autoreferencia en `CryptoFactory.java`

```java
// ANTES: 'token' no es parámetro del constructor → autoreferencia nula
this.token = token;

// CORRECCIÓN: línea eliminada
```

---

### Error 5 — `address` no asignado en `CreditCardFactory.java`

```java
// ANTES: parámetro recibido pero nunca asignado
public CreditCardFactory(..., String address) {
    // this.address = address; ← faltaba

// CORRECCIÓN
this.address = address;
```

---

## Clases que faltaban según el UML

| Clase/Interface | Estado antes | Acción tomada |
|-----------------|-------------|---------------|
| `PaymentFactory` | No existía | Creada como interface |
| `CreditCardFactory implements PaymentFactory` | Solo extendía `PaymentMethod` | Agregado `implements` + override |
| `PaypalFactory implements PaymentFactory` | Solo extendía `PaymentMethod` | Agregado `implements` + override |
| `CryptoFactory implements PaymentFactory` | Solo extendía `PaymentMethod` | Agregado `implements` + override |

---

## Validación del Diagrama de Contexto

El diagrama de contexto (`docs/imagenes/contexto.png`) es **suficiente y pertinente**. Muestra correctamente:
- El flujo cliente → sistema de pago
- La notificación automática a los tres módulos (inventario, facturación, notificaciones)
- La relación de correo al cliente desde el módulo de notificación

**Cambio sugerido:** el diagrama no representa explícitamente los dos patrones de diseño (Factory y Observer). Sería útil agregar una nota o leyenda que los identifique, ya que son el núcleo de la arquitectura.

---

## Evidencia de Compilación y Tests

### Compilación

```
.\mvnw.cmd compile
→ Compiling 23 source files
→ BUILD SUCCESS
```

### Ejecución de Tests

```
.\mvnw.cmd test

Tests run: 1, Failures: 0, Errors: 0, Skipped: 0

BUILD SUCCESS
Total time: 24.970 s
```

> Los warnings de Mockito/JDK 21 sobre carga dinámica de agentes son advertencias de compatibilidad futura del JDK, no errores del código del proyecto.

---

## Estructura Final del Paquete `util`

```
util/
├── PaymentFactory.java        ← NUEVA — interface Abstract Factory
├── PaymentMethod.java         ← CORREGIDA — bug constructor customerID
├── ValidatePayment.java       ← sin cambios
├── PaymentStatus.java         ← sin cambios
├── PaymentObserver.java       ← sin cambios
├── PaymentEventObserver.java  ← CORREGIDA — import incorrecto eliminado
├── ECIPayment.java            ← sin cambios (ya era correcto)
├── CreditCardFactory.java     ← CORREGIDA — implements PaymentFactory + address
├── PaypalFactory.java         ← CORREGIDA — implements PaymentFactory
├── CryptoFactory.java         ← CORREGIDA — implements PaymentFactory + token bug
├── Facturation.java           ← sin cambios
├── Inventory.java             ← sin cambios
├── Notification.java          ← sin cambios
└── Product.java               ← sin cambios
```
