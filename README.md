# Routing Service

A simple Spring Boot application that calculates land routes between countries based on their shared borders.  
The service loads a public dataset of countries, builds a graph of neighboring states, and finds the shortest land route between two points.

---

## 📊 Dataset

The application uses the following dataset:

```
https://raw.githubusercontent.com/mledoze/countries/master/countries.json
```

Each country entry contains, among other fields:

- `cca3` — three‑letter country code
- `borders` — list of neighboring countries
- `latlng` — approximate geographic centroid
- `capitalInfo.latlng` — coordinates of the capital city

---

## 🔌 API

### `GET /routing/{origin}/{destination}`

Returns the shortest possible land route between two countries.  
The route is computed using **Breadth‑First Search (BFS)**, which is the correct algorithm for this dataset.

**Example:**

```
GET /routing/CZE/ITA
```

**Response:**

```json
{
  "route": ["CZE", "AUT", "ITA"]
}
```

If no land route exists, the service returns **HTTP 400**.

---

## 🧠 Why BFS?

The dataset provides only **which countries border each other**, but does **not** include:

- border geometry
- border length
- border crossing locations
- road networks
- actual distances between border points

Because of this, the only reliable metric is **the number of borders crossed**.  
BFS is the optimal algorithm for finding the shortest path in an unweighted graph.

---

## 🗺️ Note on Weighted Routing

A weighted routing variant was explored, using geographic distances between capital cities as edge weights.

However, this approach produces unrealistic routes because the dataset:

- does not include real border crossing points
- does not include border shapes
- uses centroids or capital cities, which may lie far from actual borders
- heavily distorts distances for large countries (e.g., France, Spain, Germany)

As a result, weighted routing tends to prefer routes through the Balkans and North Africa, which appear “shorter” according to the dataset but are not realistic.

For these reasons, the BFS‑based solution is considered the **correct and intended approach**.

---

## 📁 Project Structure

```
src/main/java/com/martinsladek/demo/routeplanner
 ├── controller
 │     └── RoutingController.java
 ├── service
 │     ├── CountryService.java
 │     └── RoutingService.java
 ├── model
 │     └── Country.java
 └── RoutePlannerApplication.java
```

---

## 📦 Build and run

### Build

Using local Maven:

```
mvn clean install
```

Using Maven wrapper:

```
./mvnw clean package
```

### Run

Using local Maven:

```
mvn spring-boot:run
```

Using Maven wrapper:

```
./mvnw spring-boot:run
```

The application runs at:

```
http://localhost:8080
```

### Example call

```
curl -i -X GET -H "Accept: application/json" http://localhost:8080/routing/CZE/ITA
```

---

## 🧪 Tests

### Unit test BFS (mock CountryService)

```
./mvnw test
```

---

## 🐳 Docker

### Build image

```
docker build -t routeplanner .
```

### Run container

```
docker run --name routeplanner -p 8080:8080 routeplanner
```

### Example call

```
curl -i -X GET -H "Accept: application/json" http://localhost:8080/routing/CZE/ITA
```

### Clean up

```
docker stop routeplanner
docker rm routeplanner
docker rmi routeplanner
```

---

## ⚙️ GitHub Actions (CI)

Workflow: `.github/workflows/build.yml`

- build
- test
- JDK 17
- Maven Wrapper

---

## 📄 License

MIT License

---

## 📝 Summary

- The BFS solution is accurate, fast, and aligned with the dataset and assignment requirements.
- Weighted routing was implemented as an experimental extension, but the dataset is not suitable for realistic weighted pathfinding.
- The application is simple to run and requires no external configuration.
