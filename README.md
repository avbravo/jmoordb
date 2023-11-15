# jmoordb
Object Documment Mapper for Java 
Mapper for MongoDB ,OrientDB and Couchbase

## Importante

Hemos creado nuevas implementaciones que estan disponibles en los siguientes proyectos

**JmoordbCore**

[Jmoordbcore](https://github.com/avbravo/jmoordbcore)

**JmoordbCore-NoSQL** : Integra JakartaData y JakartaNoSQL
[Jmoordbcore-nosql](https://github.com/avbravo/jmoordb-core-jnosql)




Es un Framework para integrar MongoDB/OrientDB/Cochbase con las aplicaciones Java de una manera sencilla.

Sintaxis similiar a JPA

 ##avbravo.github.io <https://avbravo.github.io/>

##Soporta
 Documentos embebidos mediante la anotación @Embedded

  Documentos relacionados mediante la anotación @Referenced
  
 ##Documentación y Libro <https://app.gitbook.com/@avbravo-2/s/jmoordb/>
 
 ##Taller Jmoordb <https://avbravo-2.gitbook.io/jmoordbtaller/>
  ```
Dependencias
 
      <dependencies>
          <dependency>
	    <groupId>com.github.avbravo</groupId>
	    <artifactId>jmoordb</artifactId>
	    <version>2.5</version>
	 </dependency>
     </dependencies>

     <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

### Entity
 

 ```

public class Paises {

   @Id
  
   private String idpais;
  
   private String pais;
  
   @Embedded
  
   private Planetas planetas;
  
   @Referenced(document="Continentes",field="idcontinente, lazy=true, facade="com.avbravo.ejb.ContinentesFacade)
  
   private Continentes continentes;
  
}

```
<h2>Repository</h2>

Las operaciones CRUD se implementan atraves de un Repository

```
import com.avbravo.jmoordb.mongodb.repository.Repository;

import com.avbravo.transporteejb.entity.Rol;

import javax.ejb.Stateless;


@Stateless

public class PaisRepository extends Repository<Pais> {
	

    public PaisRepository(){
    
        super(Pais.class);
	
    }
    
   
}
```

  <h3>save()</h3>

  ```
   Paises paises = new Paises("pa","Panama");
   
   paisesRepository.save(paises);
   
   <h3>find()</h3>
   Paises paises = paisesRepository.find("idpais","pa");
   
   ```
   
   
# jmoordb Documentación y Libro <https://www.gitbook.com/book/avbravo/jmoordb/details>

Publicaciones 
Adam Bien 
JPA AND MICROSERVICES, RXJAVA, CRUD AND TX, GRADLE, CROSS FIELD VALIDATION, LICENSING, MDA OR TOPICS FOR THE 35TH AIRHACKS.TV
<http://adambien.blog/roller/abien/entry/jpa_and_microservices_rxjava_crud>

Video
<https://www.youtube.com/watch?v=eSoJYBOgIHk>


## Release
### release-2.5
   - Soporte para MongoDB Java Driver 4.0+
   - Ya no necesitas usar la clase JmoordbConection
```
//            JmoordbConnection jmc = new JmoordbConnection.Builder()
//                    .withSecurity(false)
//                    .withDatabase("basededato")
//                    .withHost("locahost")
//                    .withPort(27017)
//                    .withUsername("")
//                    .withPassword("")
//                    .withUri("")
//                    .build();

```

   - En su lugar se integro Microprofile Config
     Define el uri de conexion mongodb.uri en el archivo microprofile-config.properties
```
Atlas
mongodb.uri=mongodb+srv://user:password@mogodbAtlas

Local

mongodb.uri=mongodb://localhost:27017
```

