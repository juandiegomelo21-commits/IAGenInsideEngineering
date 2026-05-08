# Solución — Problema #1: El Videoclub de Don Mario

## Información General

| Campo | Detalle |
|-------|---------|
| **IA utilizada** | Claude Sonnet 4.6 (Anthropic) |
| **Tiempo invertido** | ~14 minutos |
| **Modalidad** | 100% asistido por IA generativa |

---

## Prompts Utilizados

### Prompt 1
> "dame contexto de los problemas y explicame tu estrategia de solucion para cada uno de los problemas"

**Resultado:** La IA analizó el repositorio completo, leyó el README, revisó el código existente del Problema #2 y entregó un análisis de los dos problemas con estrategias de solución para cada uno.

---

### Prompt 2
> "pero mira el problema numero 1 explicamelo"

**Resultado:** La IA explicó en detalle el enunciado del Videoclub: las reglas del catálogo, la lógica de membresías, el formato del recibo esperado, y propuso los patrones de diseño a aplicar (Strategy + herencia con polimorfismo) junto con los principios SOLID relevantes.

---

### Prompt 3
> "implementalo por favor"

**Resultado:** La IA generó los 8 archivos Java de la solución completa en el paquete `videoclub`, los compiló en aislamiento y ejecutó dos casos de prueba verificando la salida por consola.

---

## Resumen de la Solución

### Patrones de Diseño Aplicados

**Strategy** — para el cálculo de precio según membresía:
- `Membresia` es la interfaz con los métodos `calcularTotal()` y `calcularDescuento()`
- `MembresiaBasica` implementa precio sin descuento
- `MembresiaPremium` implementa 20% de descuento
- `Videoclub` opera sobre la interfaz sin conocer la implementación concreta → fácil agregar nuevas membresías

**Polimorfismo con herencia** — para los tipos de película:
- `Pelicula` clase abstracta con método `getTipo()` abstracto
- `PeliculaFisica` → `getTipo()` retorna `"Fisica"`
- `PeliculaDigital` → `getTipo()` retorna `"Digital"`
- El catálogo y el recibo operan sobre `List<Pelicula>` sin distinguir el tipo concreto

### Principios SOLID Aplicados

| Principio | Aplicación |
|-----------|-----------|
| **SRP** | `Pelicula` representa datos, `Membresia` calcula precios, `Videoclub` orquesta, `VideoClubApp` es el punto de entrada |
| **OCP** | Agregar membresía `Gold` o `PeliculaStreaming` no requiere modificar código existente |
| **LSP** | `PeliculaFisica` y `PeliculaDigital` son intercambiables en cualquier lugar que espere `Pelicula` |

### Estructura de Archivos

```
src/main/java/.../videoclub/
├── Pelicula.java           ← clase abstracta base
├── PeliculaFisica.java     ← subclase tipo físico
├── PeliculaDigital.java    ← subclase tipo digital
├── Membresia.java          ← interfaz Strategy
├── MembresiaBasica.java    ← sin descuento
├── MembresiaPremium.java   ← 20% descuento
├── Videoclub.java          ← lógica: catálogo, selección, recibo
└── VideoClubApp.java       ← main() para ejecución por consola
```

---

## Evidencia de Ejecución

### Caso 1 — Cliente Premium, películas 1 y 3

**Entrada:**
```
Membresía: 2 (Premium)
Películas: 1,3
```

**Salida por consola:**
```
Peliculas disponibles:
--------------------------------------------------
1. [Fisica] Interestellar - $8.000 - Disponible
2. [Fisica] El Padrino - $7.000 - No disponible
3. [Digital] Inception - $5.000 - Disponible
4. [Digital] Matrix - $6.000 - Disponible
--------------------------------------------------

Tipo de membresia:
  1. Basica  (precio normal)
  2. Premium (20% de descuento)
Seleccione (1/2):

Seleccione peliculas (numeros separados por coma):

--- RECIBO DE ALQUILER ---
Cliente: Premium
Peliculas:
 - Interestellar (Fisica) - $8.000
 - Inception (Digital) - $5.000
Subtotal: $13.000
Descuento (20%): $2.600
Total a pagar: $10.400
--------------------------
¡Disfrute su pelicula!
```

> Resultado coincide exactamente con el recibo de ejemplo del enunciado.

---

### Caso 2 — Cliente Básico, intento de seleccionar película no disponible

**Entrada:**
```
Membresía: 1 (Básica)
Películas: 1,2,4  (El Padrino no está disponible)
```

**Salida por consola:**
```
"El Padrino" no esta disponible (ignorada)

--- RECIBO DE ALQUILER ---
Cliente: Basica
Peliculas:
 - Interestellar (Fisica) - $8.000
 - Matrix (Digital) - $6.000
Subtotal: $14.000
Total a pagar: $14.000
--------------------------
¡Disfrute su pelicula!
```

> El sistema ignora correctamente la película no disponible e informa al usuario.

---

## Compilación

```
javac -d target/videoclub-test src/.../videoclub/*.java
→ BUILD SUCCESS (0 errores)
```

> Nota: El proyecto Maven completo no compila aún porque el Problema #2 tiene errores pendientes de corrección independientes a esta solución.
