# Sistema de Matriculación

Aplicación web para la gestión académica y administrativa de un instituto educativo. El sistema centraliza estudiantes, docentes, materias, cursos y matrículas mediante una API REST desarrollada con Spring Boot y una interfaz web desarrollada con Angular.

> Proyecto académico con una arquitectura preparada para desarrollo local, pruebas y evolución hacia un entorno productivo.

## Índice

- [Características](#características)
- [Arquitectura](#arquitectura)
- [Tecnologías](#tecnologías)
- [Requisitos](#requisitos)
- [Configuración segura](#configuración-segura)
- [Instalación y ejecución](#instalación-y-ejecución)
- [API REST](#api-rest)
- [Pruebas](#pruebas)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Publicación en GitHub](#publicación-en-github)
- [Consideraciones de seguridad](#consideraciones-de-seguridad)

## Características

- Autenticación y registro de usuarios mediante tokens JWT.
- Autorización basada en roles y permisos.
- Gestión CRUD de estudiantes, docentes, materias y matrículas.
- Gestión de cursos, aulas, carreras, institutos y periodos académicos.
- Persistencia relacional con MySQL mediante Spring Data JPA e Hibernate.
- Auditoría de cambios en entidades relevantes mediante Hibernate Envers.
- Inicialización idempotente del rol administrador y datos académicos base.
- Interfaz Angular integrada en el backend Spring Boot.
- Separación de credenciales y secretos mediante variables de entorno.

## Arquitectura

El proyecto utiliza una arquitectura cliente-servidor:

```text
Angular 20
    │
    │ HTTP + JSON + JWT
    ▼
Spring Boot 4.1.1 / API REST
    │
    │ Spring Data JPA / Hibernate
    ▼
MySQL 8
```

En desarrollo, Angular puede ejecutarse con su servidor local. Para distribuir la aplicación como una sola unidad, el frontend se compila en `matriculacion/src/main/resources/static` y Spring Boot sirve los archivos generados junto con la API.

## Tecnologías

### Backend

- Java 17+
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Security
- Spring Data JPA
- Hibernate Envers
- MySQL Connector/J
- JSON Web Token (JJWT 0.12.6)
- Maven Wrapper

### Frontend

- Angular 20
- TypeScript
- RxJS
- Node.js y npm

## Requisitos

Instala las siguientes herramientas antes de ejecutar el proyecto:

- Java 17 o superior.
- MySQL 8 o compatible.
- Node.js LTS y npm.
- IntelliJ IDEA, Eclipse o cualquier IDE compatible con Spring Boot (opcional).
- Git (para clonar y publicar el repositorio).

Comprueba las versiones:

```powershell
java -version
mysql --version
node --version
npm --version
```

## Configuración segura

Las credenciales y secretos no se almacenan en el repositorio. El backend requiere estas variables de entorno:

| Variable | Descripción |
| --- | --- |
| `DB_USERNAME` | Usuario de la base de datos MySQL |
| `DB_PASSWORD` | Contraseña de la base de datos MySQL |
| `JWT_SECRET` | Secreto JWT codificado en Base64, con mínimo 32 bytes |
| `ADMIN_PASSWORD` | Contraseña del usuario inicial `admin` |

El archivo `.env.example` documenta los nombres esperados sin incluir valores reales. El archivo `.env` está excluido mediante `.gitignore`.

### 1. Crear la base de datos

Ejecuta en MySQL:

```sql
CREATE DATABASE matriculacion
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
```

La aplicación crea y actualiza las tablas automáticamente mediante `spring.jpa.hibernate.ddl-auto=update`. Para producción se recomienda utilizar migraciones versionadas, como Flyway o Liquibase.

### 2. Definir las variables de entorno

En PowerShell:

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="tu-clave-de-mysql"
$env:JWT_SECRET="tu-secreto-base64"
$env:ADMIN_PASSWORD="tu-clave-inicial-de-admin"
```

Genera un secreto JWT seguro en PowerShell:

```powershell
$bytes = [byte[]]::new(32)
[System.Security.Cryptography.RandomNumberGenerator]::Fill($bytes)
[Convert]::ToBase64String($bytes)
```

También puedes definir las variables en IntelliJ IDEA desde **Run > Edit Configurations > Environment variables**, dentro de la configuración `MatriculacionApplication`.

### 3. Configuración de la aplicación

La configuración se encuentra en:

```text
matriculacion/src/main/resources/application.properties
```

Valores locales predeterminados:

- Base de datos: `localhost:3306/matriculacion`
- Puerto del backend: `8083`
- API: `http://localhost:8083/api`

No publiques contraseñas, tokens, archivos `.env` ni configuraciones locales. Si una credencial se expuso anteriormente, reemplázala antes de publicar el repositorio.

## Instalación y ejecución

### Opción A: ejecutar backend y frontend integrado

Desde la carpeta `matriculacion/`:

```powershell
.\mvnw.cmd spring-boot:run
```

Abre:

```text
http://localhost:8083/
```

### Opción B: ejecutar Angular en desarrollo

Desde la carpeta `frontend/`:

```powershell
npm install
npx ng serve
```

La aplicación Angular utilizará la API configurada en `frontend/src/environments/environment.ts`:

```text
http://localhost:8083/api
```

Para compilar Angular y generar los archivos estáticos que sirve Spring Boot:

```powershell
npm run build
```

## API REST

La API está disponible bajo el prefijo `/api`.

### Autenticación

| Método | Endpoint | Descripción |
| --- | --- | --- |
| `POST` | `/api/auth/login` | Iniciar sesión y obtener un JWT |
| `POST` | `/api/auth/registro` | Registrar un usuario |

### Recursos académicos

Los recursos CRUD utilizan `GET`, `POST`, `PUT` y `DELETE`:

| Recurso | Endpoint |
| --- | --- |
| Estudiantes | `/api/estudiantes` |
| Docentes | `/api/docentes` |
| Materias | `/api/materias` |
| Matrículas | `/api/matriculas` |

Operaciones disponibles para cada recurso:

```text
GET    /api/recurso
GET    /api/recurso/{id}
POST   /api/recurso
PUT    /api/recurso/{id}
DELETE /api/recurso/{id}
```

Las operaciones protegidas requieren:

```text
Authorization: Bearer <token>
Content-Type: application/json
```

Ejemplo de inicio de sesión:

```json
{
  "username": "admin",
  "password": "la-clave-configurada-en-ADMIN_PASSWORD"
}
```

El usuario `admin` se crea o actualiza al iniciar la aplicación. Su contraseña siempre se toma de `ADMIN_PASSWORD`; si la variable no está definida, el backend no debe ejecutarse.

## Pruebas

Para ejecutar las pruebas del backend desde `matriculacion/`:

```powershell
.\mvnw.cmd test
```

Para ejecutar las pruebas del frontend desde `frontend/`:

```powershell
npm test
```

También puedes validar manualmente la API con Postman:

1. Ejecuta `POST http://localhost:8083/api/auth/login`.
2. Copia el campo `token` de la respuesta.
3. En una petición protegida, selecciona **Authorization > Bearer Token**.
4. Pega el token y prueba, por ejemplo, `GET http://localhost:8083/api/estudiantes`.

## Estructura del proyecto

```text
.
├── frontend/                         # Aplicación Angular
│   ├── src/app/core/                 # Autenticación, guardas e interceptores
│   ├── src/app/features/             # Pantallas y funcionalidades
│   └── package.json
├── matriculacion/                    # Aplicación Spring Boot
│   ├── src/main/java/...             # Controladores, servicios y entidades
│   ├── src/main/resources/           # Configuración y recursos estáticos
│   ├── src/test/                     # Pruebas del backend
│   └── pom.xml
├── .env.example                      # Plantilla de variables, sin secretos
├── .gitignore
└── README.md
```

## Publicación en GitHub

Desde la raíz del proyecto:

```powershell
git init
git add .
git status
```

Antes de confirmar, verifica que `git status` no muestre `.env`, contraseñas, `target/`, `node_modules/` ni archivos de IDE.

Después crea el commit y enlaza tu repositorio:

```powershell
git commit -m "Documenta sistema de matriculacion"
git branch -M main
git remote add origin https://github.com/TU_USUARIO/TU_REPOSITORIO.git
git push -u origin main
```

## Consideraciones de seguridad

- Usa secretos diferentes para desarrollo, pruebas y producción.
- No utilices el usuario `root` de MySQL en producción; crea un usuario con permisos mínimos.
- Mantén `JWT_SECRET` fuera del código y rótalo si se expone.
- Utiliza HTTPS en cualquier entorno accesible por red.
- No incluyas tokens JWT, contraseñas ni respaldos de la base de datos en Git.
- Revisa los archivos antes de cada publicación con `git status` y, si es necesario, con un detector de secretos.

## Licencia

Este proyecto no declara una licencia de uso en el repositorio. Si deseas permitir su reutilización, añade una licencia, por ejemplo MIT, antes de publicarlo.
