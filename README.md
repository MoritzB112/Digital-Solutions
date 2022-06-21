# Bienvenido a Digital Solutions :computer: 
En este repositorio podrás encontrar nuestro proyecto finalista de la asignatura Sistemas de la Información para Internet de la Universidad de Málaga. Dicho proyecto consiste en la elaboración de una aplicación Web empresarial basada en Java EE siguiendo el modelo de finanzas tecnológicas 'Fintech' de la empresa Ebury.
- Accede a la [presentación](https://github.com/MoritzB112/Digital-Solutions/blob/main/Presentation%20Slides/Slides%20Digital%20Solutions.pdf) para conocer de primera mano nuestro proyecto, la cual fue elaborada para su defensa ante el tribunal.

## 📌 Nuestro equipo
- [Moritz Baader](https://github.com/MoritzB112)
- [Ariadna Medina Guerrero](https://github.com/Ariadna-Medina)
- [Francisco Velasco Romero](https://github.com/franVR10)
- [Marcos Hidalgo Baños](https://github.com/MarkosHB)

## Requisitos de la aplicación
1)	#### **Acceso a la aplicación de personal administrativo del banco.** <br> 
      El personal administrativo del banco tendrá un acceso (con usuario y contraseña) diferente al de los clientes y autorizados para acceder a la aplicación y realizar operaciones con las cuentas, clientes y autorizados.
2)	#### **Alta de un cliente en el sistema.** <br> 
      La aplicación permitirá a un administrativo dar de alta a clientes en el sistema. Los clientes pueden ser personas físicas o jurídicas. La información que es necesario almacenar de un cliente se indica abajo, en los requisitos de información.
3)	#### **Modificación de datos de un cliente.** <br> 
      La aplicación debe permitir a un administrativo modificar los datos de un cliente.
4)	#### **Baja de un cliente.** <br> 
      La aplicación permitirá a un administrativo dar de baja a clientes del banco. Los clientes del banco no pueden eliminarse físicamente de la base de datos, ya que pueden ser necesarios por motivos de auditorías. La baja de un cliente implica que no es posible para el cliente operar en el sistema. Solo se puede dar de baja un cliente que no tenga cuentas abiertas.
5)	#### **Apertura de una cuenta de cualquiera de los dos tipos considerados.** <br> 
      La aplicación permitirá a un administrativo la apertura de una cuenta. La cuenta podrá ser agrupada (pooled) o segregada (segregated). En ambos casos la(s) cuenta(s) externa(s) asociada(s) se añade(n) como información, no se hace nada más. Será necesario que haya más de una cuenta externa en el caso de una cuenta agrupada con varias divisas.
6)	#### **Añadir autorizados a la cuenta de una persona jurídica.** <br> 
      La aplicación permitirá a un administrativo añadir personas autorizadas a las cuentas que pertenezcan a cliente que son personas jurídicas. Las personas autorizadas serán las que podrán entrar en la aplicación para realizar operaciones con la cuenta.
7)	#### **Modificación de datos de un autorizado.** <br> 
      La aplicación permitirá a un administrativo modificar los datos de las personas autorizadas a operar con cuentas de clientes que son personas jurídicas.
8)	#### **Eliminar autorizados de una cuenta.** <br>
      La aplicación permitirá a un administrativo dar de baja a personas autorizadas a operar con cuentas cuyos clientes sean personas jurídicas. Estas personas no se eliminan del sistema, ya que podría ser necesario que la información conste para alguna auditoría o informe. Una persona autorizada que esté de baja no puede acceder a la cuenta en la que se encontraba autorizada.
9)	#### **Cierre de una cuenta.** <br>
      La aplicación permitirá a un administrativo cerrar una cuenta bancaria. Solo se puede cerrar una cuenta que tenga saldo 0 (en todas sus divisas). Una cuenta cerrada no se elimina, por si es necesario reportarla en algún informe.
10)	#### **Acceso a la aplicación de clientes y autorizados.** <br>
      Los clientes que sean personas físicas y los autorizados a cuentas de clientes que son personas jurídicas podrán acceder a la aplicación (con usuario y contraseña) para ver las cuentas a las que tienen acceso y consultar sus transacciones y cualquier otra información. Un cliente que es persona jurídica no puede tener acceso a la aplicación.
11)	#### **Generación de informes para Holanda.** <br>
      La aplicación implementará una API REST con tres endpoints que proporcionen la información de las cuentas y los clientes. Los detalles sobre la información a devolver se encuentran en la presentación de eBury. En el caso de que algún campo no esté disponible en el modelo de datos se devuelve "non-existent".
12)	#### **Generación de informes para Alemania.** <br>
      La aplicación será capaz de generar un fichero CSV con la información que exige Alemania (ver esta información en la presentación de eBury). Deberá haber un botón de descarga para que una persona administrativa de la empresa pueda descargarlo y posteriormente subirlo al SFTP (ajeno a la aplicación). Hay dos tipos de informes: el inicial y el periódico (con menos información). El usuario administrativo deberá poder escoger entre estos dos informes.

### Requisitos adicionales de la aplicación
-  ### **RF16	Bloquear / desbloquear cliente/autorizado.** <br>
      La aplicación permitirá a un usuario administrativo bloquear a un cliente o autorizado de manera temporal (no es lo mismo que una baja). En el caso de que el cliente bloqueado sea una persona jurídica, sus autorizados no podrán operar con al cuenta de dicho cliente. Si esa cuenta es la única a la que tienen acceso, la persona autorizada tampoco podrá acceder a la aplicación. Si el cliente es una persona física, esta no podrá acceder a la aplicación. La aplicación también permitirá a los usuarios administrativos desbloquear a los usuarios.
-  ### **RF17	Cambio de divisas realizadas por el cliente/autorizado.** <br>
      La aplicación permitirá a un cliente/autorizado realizar un cambio de divisas en una cuenta agrupada (pooled). El cambio de divisas se considerará una transacción especial donde el origen y destino es la misma cuenta. Para poder realizar un cambio de divisas será necesario que la cuenta tenga saldos en las divisas de origen y destino. Los saldos de las cuentas asociadas con la cuenta agrupada deberá actualizarse también. No será posible realizar un cambio de divisas en cuentas segregadas.
-  ### **RF18	Cambio de divisas realizadas por el administrativo.** <br>
      La aplicación permitirá a un administrativo realizar un cambio de divisas en una cuenta agrupada (pooled). El cambio de divisas se considerará una transacción especial donde el origen y destino es la misma cuenta. Para poder realizar un cambio de divisas será necesario que la cuenta tenga saldos en las divisas de origen y destino. Los saldos de las cuentas asociadas con la cuenta agrupada deberá actualizarse también. No será posible realizar un cambio de divisas en cuentas segregadas.


### Requisitos de información
- **Cliente:**	Identificación, tipo de cliente (persona física o jurídica), estado (activo, bloqueado, baja), fecha de alta, fecha de baja, dirección, ciudad, código postal, país. En el caso de personas jurídicas hace falta también la razón social. En el caso de una persona física hace falta el nombre, apellido y la fecha de nacimiento
- **Autorizado:**	Identificación, nombre, apellidos, dirección, fecha de nacimiento, estado (activo, bloqueado, baja), fecha de inicio (como autorizado) y fecha de fin (como autorizado)
- **Cuenta:**	IBAN y SWIFT. Para cuentas de eBury hace falta también el estado (activa, baja), fecha de apertura, fecha de cierre, el tipo de cuenta (agrupada o segregada) y el saldo en las distintas divisas que tiene la cuenta asociada. En el caso de las cuentas de referencia asociadas a las cuentas de eBury, además del IBAN y SWIFT necesitamos conocer el nombre del banco, la sucursal, el país, el saldo, la fecha de apertura y su estado (activa, baja).
- **Transacciones:**	Fecha de instrucción, la cantidad transferida en la divisa de origen, divisas de origen y de destino, el tipo de transacción (cambio de divisas o transferencia regular) y la comisión cobrada por el banco (supondremos que para cambios de divisas no hay comisión y para el resto se cobra una comisión de un 1% de la cantidad a transferir).

