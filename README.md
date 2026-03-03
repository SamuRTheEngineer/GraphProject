# Proyecto - Algoritmos de Recorrido de Grafos

**Ingeniería de Sistemas - Universidad EIA**

---

## Descripción

Este proyecto es originalmente pensado para cumplir con las instrucciones del trabajo de grafos de la asignatura Lenguajes y Compiladores:

> *"Desarrollar una aplicación en Java que modele el mapa de aldeas como un grafo y resuelva dos problemas fundamentales para el brujo:*
>
> *Ruta de Escape (Dijkstra): Calcular el camino más corto (menor distancia total) desde un pueblo de origen hasta la guarida.*
>
> *Ruta de Cacería (Bellman-Ford Adaptado): Calcular el camino que maximice la cantidad de víctimas recolectadas.*
>
> *Nota: Las víctimas de un nodo solo se suman la primera vez que se visita dicho nodo."*

Sin embargo, se buscó entender profundamente el concepto de **Grafo** matemáticamente, y cómo este es aplicado a las soluciones de Ingeniería.

Se exploraron distintos algoritmos de recorrido de grafos, como **BFS, DFS, Kruskal, Dijkstra y Bellman-Ford**, para entender su funcionamiento y aplicación.

Este es un programa para la visualización paso a paso de estos recorridos. Además, cumple con las instrucciones del problema inicialmente planteado usando **Dijkstra y Bellman-Ford Adaptado**.

---

## Formato de Entrada

El formato de entrada para generar el grafo es:

```
u v d c
```

* `u`: nodo fuente.
* `v`: nodo destino.
* `d`: peso de la arista.
* `c`: atributo del nodo v.

---

## Características

### Visualización en Tiempo Real

Se muestra paso a paso cómo se recorre el grafo, según el algoritmo seleccionado.

### Interactivo

Se pueden manipular los nodos del grafo, para representar el grafo con una geometría distinta, sin cambiar su estructura matemática.

### Resultados

No sólo se recorre el grafo. Se puede seleccionar un nodo destino:

* Utilizando el algoritmo de Dijkstra, podemos calcular la ruta más corta desde el nodo incial hasta el nodo seleccionado, y el costo total del recorrido.
* Cada nodo tiene un atributo. Utilizando el algoritmo de Bellman-Ford Adaptado, podemos calcular la ruta que maximiza el valor acumulado de este atributo al pasar por cada nodo (víctimas, según las instrucciones).

### Grafos Dirigidos y No Dirigidos

Se puede elegir si el grafo es dirigido o no dirigido.

---

## Tecnologías Utilizadas

* Java 17+
* JavaFX 17+

---

## Cómo Ejecutarlo

1. Clonar el repositorio.
2. Abrirlo en el IDE de Java de preferencia.
3. Asegurarse de tener configurado el SDK de JavaFX.
4. Ejecutar:

```
graphGUI/src/grafosgui/MainApp.java
```
