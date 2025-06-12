# 🎓📚 Sistema de Gestión de Estudiantes y Cursos - Lab 13 DAW

¡Bienvenid@ al repositorio del **Laboratorio 13** de la materia **Desarrollo de Aplicaciones Web**! 🚀  
Aquí encontrarás una aplicación web construida con ❤️ en **Java Spring Boot**, que permite gestionar estudiantes y cursos de forma sencilla y eficiente.

---

## 🛠️ Tecnologías Utilizadas

- ☕ **Java 17+**
- 🌱 **Spring Boot** (API REST)
- 🗃️ **Spring Data JPA** con **Hibernate**
- 🐘 **PostgreSQL** o 🐬 **MySQL**
- 🎨 **HTML + Bootstrap 5** para la interfaz web
- 🧠 **JavaScript (Fetch API)** para llamadas asincrónicas
- 🧪 **Postman** (para pruebas de endpoints)
- 💡 IDE: **IntelliJ IDEA**

---

## 🧩 Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── com.tecsup.demo01/
│   │       ├── controller/      # Controladores REST para Estudiante y Curso
│   │       ├── entity/          # Entidades JPA: Estudiante y Curso
│   │       └── repository/      # Interfaces JpaRepository
│   └── resources/
│       ├── static/              # Archivos JS, CSS
│       └── templates/           # Vistas HTML (Thymeleaf o JSP si se usara)
└── application.properties       # Configuración de base de datos
```

---

## 🧑‍🏫 ¿Qué hace esta app?

Esta app permite:

- ➕ **Crear** estudiantes y cursos
- 📝 **Editar** estudiantes y asignarles múltiples cursos
- 📋 **Listar** todos los estudiantes y cursos
- ❌ **Eliminar** registros
- 🔁 **Actualizar** datos fácilmente

---

## 🔄 Comunicación con el Backend

✅ Se usó **`fetch()`** en el frontend (JavaScript) para comunicarse directamente con los endpoints REST del backend.  
Gracias a esto:

- No fue necesario crear controladores adicionales tipo `@Controller` para servir HTML.
- Se evitó duplicar lógica de control.
- Toda la comunicación es asíncrona y en formato **JSON** 📦.
- El código del lado del cliente se encarga de enviar los datos del formulario directamente a los endpoints usando `POST` y `PUT`.

**Ejemplo de uso de fetch:**

```javascript
fetch("/api/estudiantes", {
  method: "POST",
  headers: { "Content-Type": "application/json" },
  body: JSON.stringify(estudiante)
})
```

---

## 🔗 Relación entre Estudiante y Curso

🔁 La relación es de **muchos a muchos**:

- Un estudiante puede estar en varios cursos.
- Un curso puede tener varios estudiantes.

🧩 Esto se resolvió con anotaciones JPA:

```java
@ManyToMany
@JoinTable(
  name = "estudiante_curso",
  joinColumns = @JoinColumn(name = "estudiante_id"),
  inverseJoinColumns = @JoinColumn(name = "curso_id")
)
private List<Curso> cursos;
```

---

## 🚀 Cómo ejecutar el proyecto

1. Clona este repositorio:
   ```bash
   git clone https://github.com/Vania-0731/lab_13_DAW.git
   cd lab_13_DAW
   ```

2. Configura tu archivo `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/tu_base_de_datos
   spring.datasource.username=usuario
   spring.datasource.password=clave
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Ejecuta la app:
   - Desde tu IDE con el botón de "play" ▶️
   - O en consola: `./mvnw spring-boot:run`

4. Accede a la app en tu navegador:
   ```
   http://localhost:8080/
   ```

---

## 📮 Endpoints disponibles

| Método | Endpoint                 | Descripción                       |
|--------|--------------------------|-----------------------------------|
| GET    | `/api/estudiantes`       | Lista todos los estudiantes 🧑‍🎓 |
| GET    | `/api/estudiantes/{id}`  | Obtener un estudiante específico |
| POST   | `/api/estudiantes`       | Crear un nuevo estudiante ✍️     |
| PUT    | `/api/estudiantes/{id}`  | Actualizar un estudiante 🔁      |
| DELETE | `/api/estudiantes/{id}`  | Eliminar estudiante ❌           |
| GET    | `/api/cursos`            | Lista todos los cursos 📘        |
| POST   | `/api/cursos`            | Crear un nuevo curso             |

---

## ✨ Características destacadas

- ✅ Validaciones de formulario en HTML5
- ✅ Modal de éxito con Bootstrap 🎉
- ✅ Checkbox dinámico para cursos
- ✅ Separación de responsabilidades (modelo-vista-controlador)
- ✅ Código limpio y entendible

---

## 🤝 Contribuciones

Este repositorio fue creado como parte del laboratorio de DAW en **TECSUP**.  
Siéntete libre de clonar, probar y aprender de él 🙌.

---

## 🧠 Autor

- 👩‍💻 Vania ([@Vania-0731](https://github.com/Vania-0731))
- 👨‍🏫 Proyecto guiado por el curso de DAW - TECSUP

---

## 🐞 ¿Problemas?

Si encuentras errores o tienes ideas para mejorar, no dudes en abrir un **Issue** o un **Pull Request**.

¡Gracias por visitar el repo! 💙✨
