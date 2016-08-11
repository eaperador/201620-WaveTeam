# WaveTeam

#Modelo conceptual

Este modelo conceptual está sujeto a cambios.

![](http://i.imgur.com/Maso0qj.jpg)


#Lista de integrantes y las clases a desarrollar:

|Nombre            |Correo Uniandes              |Clase a desarrollar|
|:-----------------|:----------------------------|:------------------|
|Luis Felipe Plazas|lf.plazas10@uniandes.edu.co  |Médico             |
|John Ardila       |je.ardila1501@uniandes.edu.co|Paciente           |
|Rogelio García    |r.garcia11@uniandes.edu.co   |Consultorio        |
|Daniela Mariño    |d.marino10@uniandes.edu.co   |Especialista       |
|Juan Lizarazo     |jm.lizarazo10@uniandes.edu.co|Cita               |


#Documentación del API:

## Entidad Médico

La comunicación entre el cliente y el servidor se realiza intercambiando objetos JSON que siguen el siguiente formato:

```javascript
{
    "name" : 'Carlos Valderrama'       /* Tipo String */
    "cédula" : 542382304,    		   /* Tipo Long */
    "especialidad" : 'Oftalmólogo'     /* Tipo String */
    "consultorio" : 3,  			   /* Tipo Long */
}
```

Si se solicta al servidor una lista de médcios, el servidor retorna un arreglo de esos objetos siguiendo el siguiente formato: 

 ```javascript
[ 
  	{
   	 "name" : 'Carlos Valderrama'		/* Tipo String */
   	 "cédula" : 542382304,			/* Tipo Long */
   	 "especialidad" : 'Oftalmólogo'		/* Tipo String */
   	 "consultorio" : 3,			/* Tipo Long */
	},
	{
   	 "name" : 'James Rodriguez'       	/* Tipo String */
   	 "cédula" : 548393222,			/* Tipo Long */
  	 "especialidad" : 'Cardiólogo'		/* Tipo String */
  	 "consultorio" : 23,			/* Tipo Long */
	}
]
```

### Servicios REST

La descripción del API REST se presenta a continuación:

Método|URI|Acción|Parámetros|Cuerpo|Retorno
:--:|:--:|:--:|:--:|:--:|:--:
**GET**|/doctors|Lista los registros de Médico (READ)|||Colección de registros de Médico 
**GET**|/doctors/*:cedula*|Obtener los atributos de una instancia de Médico (READ)|**@PathParam cédula**: Número de cédula del doctor a consultar||Atributos de la instancia de Médico
**POST**|/doctors|Crear una nueva instancia de la entidad Médico (CREATE)||Atributos de la instancia de Médico a crear|Instancia de Médico creada, incluyendo su cédula
**PUT**|/doctors/*:cedula*|Actualiza una instancia de la entidad Médico (UPDATE)|**@PathParam cédula**: Identificador del registro|Objeto JSON de Médico|Instancia de Médico actualizada
**DELETE**|/doctors/*:cedula*|Borra instancia de Médico en el servidor (DELETE)|**@PathParam cédula**: Identificador del registro||

## Entidad Paciente

La comunicación entre el cliente y el servidor se realiza intercambiando objetos JSON que siguen el siguiente formato:

```javascript
{
	"id" : 1,		/* Tipo Long */
	"name" : ''		/* Tipo String */
	"edad" : ''		/* Tipo int */
	"sexo" : ''		/* Tipo String */
	"tipoSangre" : ''	/* Tipo String */
	"eps" : '' 		/* Tipo String */
}
```

Si se solicta la servidor una lista de pacientes, el servidor retorna un arreglo de esos objetos siguiendo el siguiente formato:

```javascript
[
  {
    "id" : 1,		/* Tipo Long */
    "name" : ''		/* Tipo String */
    "edad" : ''		/* Tipo int */
    "sexo" : ''		/* Tipo String */
    "tipoSangre" : ''	/* Tipo String */
    "eps" : '' 		/* Tipo String */
  }, {
    "id" : 2,		/* Tipo Long */
    "name" : ''		/* Tipo String */
    "edad" : ''		/* Tipo int */
    "sexo" : ''		/* Tipo String */
    "tipoSangre" : ''	/* Tipo String */
  } /* ... otros pacientes */
]
```

### Servicio REST

Al ejecutarlo en su propia máquina, el recurso REST estará disponible en:

 • xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

La descripción del API REST se presenta a continuación:

| Método | URI | Acción | Parámetros | Cuerpo | Retorno |
|:----------|:----------|:----------|:----------|:----------|:----------|
| **GET** | /pacientes | Lista de los registros de Paciente (READ) | | | Colección de registros de Paciente|
| **GET** | /pacientes/:id | Obtiene los atributos de una instancia de Paciente (READ) que tiene indicado id | **@PathParam id**: Identificador de registro | | Atributos de la instancia de Paciente |
| **PUT** | /pacientes/:id | Actualiza la instancia de la entidad Paciente (UPDATE) | **@PathParam id**: Identificador de registro | Objeto JSON de Paciente | Instancia de Paciente actualizada |
| **POST** | /pacientes | Crea una nueva instancia de la entidad Paciente (CREATE) | | Atributos de la instancia de Paciente a crear | Instancia de City creada, incluyendo su nuevo ID |
| **DELETE** | /pacientes/:id | Borra instancia de Paciente en el servidor (DELETE) | **@PathParam id**: Identificador del registro | | |

### La clase PacienteResource tendrá entonces los siguientes métodos.

| Método | Descripción |
|:----------|:----------|
| List<PacienteDTO> getPacientes() | Retorna la lista de pacientes |
| PacienteDTO getPaciente(Long id) | Retorna el paciente identificado con este id |
| PacienteDTO createPaciente(PacienteDTO paciente) | Crea un Paciente con la información enviada como parámetro |
| PacienteDTO updatePaciente(Long id, PacienteDTO paciente) | Actualiza la información del paciete identificado con este id |
| void deletePaciente(Long id) | Borra el paciente identificado con este id|

### Anotaciones para indicar el verbo HTTP

| Anotación | Métodos | Descripción |
|:----------|:----------|:----------|
| **@GET** | List<PacienteDTO> getPacientes() | Retorna la lista de pacientes |
| **@GET** | PacienteDTO getPaciente(Long id) | Retorna el paciente identificado con este id |
| **@POST** | PacienteDTO createPaciente(PacienteDTO paciente) | Crea un Paciente con la información enviada como parámetro |
| **@PUT** | PacienteDTO updatePaciente(Long id, PacienteDTO paciente) | Actualiza la información del paciete identificado con este id |
| **@DELETE** | void deletePaciente(Long id) | Borra el paciente identificado con este id|

##Entidad Consultorio

El formato utilizado para un recurso de tipo "consultorio" es:

```javascript
{
	"Nombre": 202,                         /*Integer*/
	"Horario": '8.00am-6.00pm',            /*String*/
	"Atención urgencias":No,               /*Boolean*/
	"Unidad Cuidados Intensivos (UCI)": Sí /*Boolean*/
	/*Otros atributos*/
}
```

Donde "102" es también la ubicación del consultorio; el primer dígito es el piso
y los otros dos ubican el consultorio en el piso.

Para una solicitud de lista de consultorios, se seguirá el siguiente formato:

```javascript
{
	"consultorios":[
			{
				"Nombre": 202,                         /*Integer*/
				"Horario": '8.00am-6.00pm',            /*String*/
				"Atención urgencias":Sí,               /*Boolean*/
				"Unidad Cuidados Intensivos (UCI)": Sí /*Boolean*/
			}, {
				"Nombre": 315,
				"Horario": '8.00am-6.00pm',            /*String*/
				"Atención urgencias":No,               /*Boolean*/
				"Unidad Cuidados Intensivos (UCI)": No /*Boolean*/
			}, {
				"Nombre": 100,
				"Horario": '24 horas',                 /*String*/
				"Atención urgencias":Sí,               /*Boolean*/
				"Unidad Cuidados Intensivos (UCI)": No /*Boolean*/
			} /*Etc*/
	]
	
}
```


###Servicios REST

|Método| URI             |Acción|Parámetros|Cuerpo|Retorno|
|:----:|:---------------:|:----:|:--------:|:----:|:-----:|
|GET   |/consultorios    |Lista los registros de Consultorio (READ)|   |   |Colección de registros de Consultorio|
|GET   |/consultorios/:id|Obtener los atributos de un Consultorio (READ)|**@PathParam id:** Identificador del registro|   | Atributos del Consultorio|
|POST  |/consultorios    |Crea un nuevo Consultorio (CREATE)|   |Atributos del Consultorio que se creará|Consultorio creado (con id)|
|PUT   |/consultorios/:id|Actualiza un Consultorio (UPDATE)|**@PathParam id:** Identificador del registro|Objeto JSON del Consultorio|Consultorio actualizado|
|DELETE|/consultorios/:id|Borra un Consultorio (DELETE)|**@PathParam id:** Identificador del registro|   |   |


## Entidad Citas:


La comunicación entre el cliente y el servidos se realiza intercambiando objetos JSON que siguen el siguiente formato

```javascript
[
      {
         "id" : 1             /* Tipo Long */
         "Fecha_cita" : ''    /* Tipo Date */
         "Hora_cita" : ''     /* Tipo Long */
         "Medico_cita" : ''   /* Tipo Medico */
         "Paciente_cita" : '' /* Tipo Paciente */
      }
]
```

Si se solicita una lista de las citas en el calendario, el servidor retorna dichos objetos en el siguiente formato:

```javascript
[
      {
         "id" : 1             /* Tipo Long */
         "Fecha_cita" : ''    /* Tipo Date */
         "Hora_cita" : ''     /* Tipo Long */
         "Medico_cita" : ''   /* Tipo Medico */
         "Paciente_cita" : '' /* Tipo Paciente */
      }, {
         "id" : 2             /* Tipo Long */
         "Fecha_cita" : ''    /* Tipo Date */
         "Hora_cita" : ''     /* Tipo Long */
         "Medico_cita" : ''   /* Tipo Medico */
         "Paciente_cita" : '' /* Tipo Paciente */
      } /*... otros pacientes */
]
```




### Servicios Rest:

|   Método   |   URL      |     Acción                                            |   Parámetros     |    Cuerpo    |    Retorno                               |
|:-----------|:-----------|:------------------------------------------------------|:-----------------|:-------------|:-----------------------------------------|
|    GET     | /citas     | Da la lista de citas                                  |                  |              |Colección de las citas en el calendario   |
|    GET     | /citas/:id | Da los parámetros de una cita en la lista             |ID: id de la cita |              |Médico asignado a la cita consultada      |
|    POST    | /Citas     | Crea una nueva cita segun la información suministrada |                  | Atributos    |Nueva cita creada                         |
|    END     | /Citas/:id | Da por terminada la cita por el medico y/o la fecha   |ID: id de la cita |              |Se termina la cita correctamente          |
|   DELETE   | /Citas/:id | Borra una cita según su ID                            |ID: id de la cita |              |Se borra la cita de la lista (cancela)    |


## Entidad Especialidad

La comunicación entre el cliente y el servidor se realiza intercambiando objetos JSON que siguen el siguiente formato:

```javascript
{
    "id" : 1                         /* Tipo Long */
    "nombre" : ''                    /* Tipo String */
    "instrumentacion" : ''           /* Tipo String */
    "doctores" :  		     /* Tipo Doctor*/
}
```
Si se solicita una lista de las especialidades, el servidor retorna dichos objetos en el siguiente formato:

```javascript
[
      {
         "id" : 1                     /* Tipo Long */
         "nombre" : ''                /* Tipo String */
         "instrumentacion" : ''       /* Tipo String */
         "doctores" :  		      /* Tipo Doctor*/
      }, {
          "id" : 2                     /* Tipo Long */
         "nombre" : ''                /* Tipo String */
         "instrumentacion" : ''       /* Tipo String */
         "doctores" :  		      /* Tipo Doctor*/
      } /*... otras especialidades */
]
```
### Servicios Rest:

Al ejecutarlo en su propia máquina, el recurso REST estará disponible en:

 • xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
 
La descripción del API REST se presenta a continuación:

|   Método   |   URL      |     Acción                                            |   Parámetros     |    Cuerpo    |    Retorno                               |
|:-----------|:-----------|:------------------------------------------------------|:-----------------|:-------------|:-----------------------------------------|
|    GET     | /Especialidades     | Da la lista de especialidades(READ)  |    |   |Colección de las especialidades del hospital   |
|    GET     | /Especialidades/:id | Da los parámetros de una especialidad (READ)|**@PathParam id:** id de la especialidad |     |Atributos de la especialidad  |
|    POST    | /Especialidades     | Crea una nueva especialidad segun la información suministrada(CREATE)|    | Atributos    |Nueva especialidad creada  |
|   DELETE   | /Especialidades/:id | Borra una especialidad según su ID (DELETE) |**@PathParam id:** id de la especialidad |      |Se borra la especialidad de la lista  |


### La clase EspecialidadResource tendrá entonces los siguientes métodos.

| Método | Descripción |
|:----------|:----------|
| List<EspecialidadDTO> getEspecialidades() | Retorna la lista de especialidades |
| EspecialidadDTO getEspecialidad(Long id) | Retorna la especialidad identificada con este id |
| EspecialidadDTO createEspecialidad(EspecialidadDTO especialidad) | Crea una especialidad con la información enviada como parámetro |
| void deleteEspecialidad(Long id) | Borra la especialidad identificada con este id|

### Anotaciones para indicar el verbo HTTP

| Anotación | Métodos | Descripción |
|:----------|:----------|:----------|
| **@GET** | List<EspecialidadDTO> getEspecialidades() | Retorna la lista de especialidades |
| **@GET** | EspecialidadDTO getEspecialidad(Long id) | Retorna la especialidad identificada con este id |
| **@POST** | EspecialidadDTO createEspecialidad(EspecialidadDTO especialidad) | Crea una especialidad con la información enviada como parámetro |
| **@DELETE** | void deleteEspecialidad(Long id) | Borra la especialidad identificado con este id|
