Ferramenta Sakai de curso de idioma a distância Read in Web
=========

## Pré-requisitos para Sakai 2.9.x

Utilizando maintenance branch do LMS Sakai, da versão 2.9, é necessário os seguintes softwares (Alguns softwares são recomendações, como o SO e o SGBD utilizado):

1. Sistema operacional *nix, como GNU/Linux ou FreeBSD
2. Software de controle de versão Subversion (SVN)
3. SGBD MySQL 5 (ou MariaDB)
4. Java Development Kit 6 (JDK) da Oracle. Essa é a versão recomendada, mas é
possível utilizar OpenJDK 7 e similares
5. Gerenciador de projeto e dependências Maven 2
6. Servidor Web para Java Servlets Apache Tomcat 5.5 (pode­se utilizar o 7, contanto
que se utilize o classloader do 5.5)

## Pré-requisitos para Read in Web 4.0.0

Para a versão nova versão do Readinweb, 4.0.0, os pré­requisitos são os mesmos do LMS Sakai, já que a software foi criado como uma ferramenta do mesmo. Assim, para compilar e implantar o software, é necessário colocá­lo no diretório do código fonte do Sakai juntamente com as outras ferramentas do núcleo do LMS.

## Instalação do Sakai 2.9.x

Esse guia de instalação é feito em área de usuário, de forma que a instalação não invada área de sistema. Pode­se rodar o LMS tanto como usuário, respeitando a condição de portas (não podem ser utilizadas portas de sistema) quanto como administrador, inclusive utilizando a porta 80, comum em servidores web.
Uma segunda forma de subir o LMS para ser acessado na porta 80 é subindo em uma porta como 8080, em área de usuário e fazer um proxy no Web Server padrão utilizado com subdomínio apropriado.

### SVN - Subversion

A instalação do Subversion pode ser feita tanto através das recomendações do próprio site h ttp://subversion.apache.org, quanto do próprio sistema operacional. Após a instalação, faça download do código:
$svncohtps:/source.sakaiproject.org/svn/sakai/branches/sakai-2.9.x

### Java 6

É recomendado instalar a versão do JDK da Oracle. Porém é possível instalar a OpenJDK ou afins de acordo com a distribuição. Pra instalar a Oracle JDK 6 ou 7 recomendamos os seguintes guias:

- http://www.oracle.com/technetwork/java/javase/downloads/java­archive­downloads­jav ase6­419409.html
- http://www.freebsd.org/java/install.html

#### Configuração

Configure as opções do Java através da variável J AVA_OPTS, elas devem ter os seguintes valores, já considerando as configurações do servidor Tomcat:

    $exportJAVA_OPTS="-server-Xmx1028m-X:MaxPermSize=320m\ -Dorg.apache.jasper.compiler.Parser.STRICT_QUOTE_ESCAPING=false\ -Djava.awt.headles=true-Dcom.sun.management.jmxremote\ -Dsun.lang.ClasLoader.alowAraySyntax=true\ -Duser.language=pt-Duser.region=BR"

Esse comando pode ser adicionado ao arquivo de s tartup.sh dentro do diretório b in do Tomcat de forma a garantir que as opções sejam utilizadas. Outra forma é coloca­las, assim com as do Maven 2, no arquivo de . bash_profile. 

### MySQL

Utiliza­se o banco de dados MySQL para instalação do Sakai para esse caso. É possível utilizar outros SGBDs, porém o MySQL foi escolhido em tempo de projeto devido ao seu extenso uso. Uma instalação da própria distribuição é suficiente apra utilização no Sakai.  
Após instalação, crie um banco de dados para o Sakai:

    $mysql-urot-p
    MySQL>createdatabasesakaidefaultcharactersetutf8; 
    MySQL>grantalprivilegesonsakai.*to'sakai'@'localhost'->identifiedby'senha';
    MySQL>flushprivileges; MySQL>quit

### Apache Tomcat 7

Com o Java instalado, pode­se então instalar o Servidor Web. Recomenda­se a instalação de uma versão não empacotada do servidor que pode ser encontrada no site http://tomcat.apache.org/download­70.cgi, para versão 7
Após fazer o download, você pode desempacotar o arquivo em seu diretório de preferência, por exemplo, / opt e fazer algumas configurações básicas
`$cd/opt $tarxvpfapache-tomcat-7.0.42.tar.gz $ln-sapache-tomcat-7.0.42tomcat7 $cdtomcat7`
Edite o arquivo c onf/server.xml a lterando a linha < Conectorport="8080"adicionando URIEncoding="UTF-8"para fixar o encoding utilizado globalmente para UTF­8, delete as aplicações padrão:
`$rm-rfwebaps/*`
E por fim altere o classloader padrão utilizado em c onf/catalina.properties. 
Adicione o seguinte à linha que começa com "common.loader=..."
`,${catalina.base}/comon/clases/,${catalina.base}/comon/lib/*.jar`
Adicione o seguinte a linha que começa com "shared.loader=..."
`${catalina.base}/shared/clases/,${catalina.base}/shared/lib/*.jar`
Adicione o seguinte a linha que começa com "server.loader=..."
`${catalina.base}/server/clases/,${catalina.base}/server/lib/*.jar`
Crie alguns diretórios necessários para rodar a aplicação corretamente:
`$mkdir-pshared/clasesshared/libcomon/clasescomon/libserver/clasesserver/lib $mkdirsakai`

### Configuração do Sakai no Tomcat

Primeiro, copie o arquivo de configuração de exemplo de dentro da instalação do Sakai para dentro do diretório Sakai recém criado:
`$cp/path/to/sakai/reference/docs/sakai.propertiessakai/`
Depois altera­se o arquivo para configurações. Uma lista de opções pode ser encontrada em: https://confluence.sakaiproject.org/display/DOC/Sakai+Properties+Reference e as configurações irão variar de acordo com as necessidades do servidor. A configuração de banco de dados ficará parecida com:

    # Theusernameandpasword. Thedefaultsarefortheout-of-the-boxHSQLDB. Changetomatch yoursetup.
    username@javax.sql.BaseDataSource=sakai
    pasword@javax.sql.BaseDataSource=senha

    ## MySQLsetings - makesuretoalterasapropriate 
    vendor@org.sakaiproject.db.api.SqlService=mysql
    driverClasName@javax.sql.BaseDataSource=com.mysql.jdbc.Driver
    hibernate.dialect=org.hibernate.dialect.MySQL5InoDBDialect url@javax.sql.BaseDataSource=jdbc:mysql:/127.0.0.1:306/sakai?useUnicode=true&characterEncoding =UTF-8
    validationQuery@javax.sql.BaseDataSource=select1fromDUAL defaultTransactionIsolationString@javax.sql.BaseDataSource=TRANSACTION_READ_COMITED`

Para que o Sakai consiga se comunicar com o banco de dados, é necessário que se instale a biblioteca de conexão de banco de dados, ou conector. Para isso, vá em http://dev.mysql.com/downloads/connector/j/, faça download do conector (necesário login), desempacote (será um arquivo j ar) e coloque­o no diretório l ib, da sua instalação do Tomcat.
 
### Maven 3

A versão final do curso Read in Web utiliza Maven 3. É comum que as distribuições tragam a versão mais nova do Maven, logo, é possível que não seja necessário a instalação não empacotada.
Caso não haja uma versão empacotada do Maven na versão 3, utilize a página de download e guia no link: h ttp://maven.apache.org/download.cgi

#### Configuração do Maven

Para evitar problemas de alocação de memória no Maven, altera­se a configuração MVN_OPTS para:

    $exportMAVEN_OPTS="-Xms256m-Xmx512m-X:PermSize=64m-X:MaxPermSize=128m"

### Compilando o Sakai

Para compilar o Sakai, entre no diretório do código fonte baixado e compile o código:
`$cd/path/to/sakai_2.9.x $mvn-Dfile.encoding=UTF-8-e-Dmaven.test.skip=truecleaninstal`

Esse processo pode levar alguns minutos, devido ao fato de que o gerenciador de pacotes Maven irá fazer download dos pacotes e bibliotecas necessárias para compilação das ferramentas. Ao final da compilação, teremos o sakai compilado e colocado no respositório local do Maven localizado em  ~/.m2/repository. Nesse mesmo diretório, vamos criar o arquivo s etings.xml, o qual conterá informações para implantação das ferramentas.
`$nano~/.m2/setings.xml`

Esse arquivo terá o seguinte conteúdo, lembrando que deve­se substituir os dados necessários para que estejam de acordo com a configuração da instalação do Apache Tomcat

    <setingsxmlns="htp:/maven.apache.org/POM/4.0.0" xmlns:xsi="htp:/w.w3.org/201/XMLSchema-instance" xsi:schemaLocation="htp:/maven.apache.org/POM/4.0.0
    htp:/maven.apache.org/xsd/setings-1.0.0.xsd">
    <profiles> <profile>
    <id>tomcat-sakai</id> <activation>
    <activeByDefault>true</activeByDefault> </activation>
    <properties> <apserver.id>tomcat-sakai</apserver.id> <apserver.home>/opt/tomcat7</apserver.home> <maven.tomcat.home>/opt/tomcat7</maven.tomcat.home>
    <sakai.apserver.home> /opt/tomcat7
    </sakai.apserver.home> <surefire.reportFormat>plain</surefire.reportFormat>
    <surefire.useFile>false</surefire.useFile> </properties>
    </profile> </profiles>
    </setings>

### Implantação do Sakai

Tem­se então o Sakai compilado, o Tomcat e o Maven configurados, assim como o MySQL, vamos então fazer a implantação da aplicação:
`$cd/path/to/sakai_2.9.x $mvnsakai:deploy`

Esse comando envia as ferramentas que compõe o Sakai para o diretório webapps, dentro da instalação do Tomcat em formato . war. Ao final, roda­se o servidor:
`$cd/opt/tomcat7/bin $./startup.sh`
O Tomcat, com as ferramentas do Sakai, demora cerca de 1 a 2 minutos pra ser inteiramente inicializado. Após a inicializado, pode ser acessado em: htp:/servidor:8080/portal
 
## Instalação do Read in Web

A instalação do Read in Web, como ferramenta não é muito diferente da instalação do próprio Sakai. Nesse momento, tem­se quase tudo configurado para download, compilação e implatação do curso.
Primeiramente, desligue o servidor Tomcat:
`$cd/opt/tomcat7/bin $./shutdown.sh`

Faça o download da ferramenta e coloque­a no diretório fonte do Sakai, entre no diretório e execute o comando Maven que resolve as dependencias, compila a ferramenta e implanta no servidor Tomcat:
`$cp-rreadinweb/path/to/sakai $cd/path/to/sakai/readinweb $mvn-Dfile.encoding=UTF-8-ecleaninstalsakai:deploy`

No arquivo s akai.properties, crie a variável: r eadinweb.upload, que apontará para um caminho no sistema operacional (/ path/to/tomcat/webaps/course-data) acessível via web (h tp:/example.com/course-data, pelo exemplo). Nesse exemplo, utilizamos o próprio T omcat para servir os arquivos, porém, é possível que eles sejam servidos de qualquer servidor web, contanto que seu caminho seja acessivel pelo Sistema Operacional. O Curso Read in Web será carregado automaticamente no processo de instalação. Isso se deve ao fato de que o curso é o principal dentro do contexto do Projeto Read in Web. Para o diretório de dados, será necessário copiar os dados do curso, presentes no diretório data dentro projeto /path/to/readinweb/course-tol/data. 

## Criação de documentação Javadoc
Para criar a documentação para utilização de novos desenvolvedores, basta rodar, no diretório do projeto o comando maven para tal:
`$mvn-Dmaven.test.skip=truejavadoc:jarinstal`

## Referências

Setup de instalação para desenvolvedores:

- https://confluence.sakaiproject.org/pages/viewpage.action?pageId=75667802
- https://confluence.sakaiproject.org/display/BOOT/Development+Environment+Setup+Walkthrough

Source install:

- https://confluence.sakaiproject.org/pages/viewpage.action?pageId=37290386

Referencias do sakai.properties:

- https://confluence.sakaiproject.org/display/DOC/Sakai+Properties+Reference
     
Guia do Administrador de Sistemas para Sakai:

- https://confluence.sakaiproject.org/display/DOC/Sys+Admin+Guide

Guia de um Help Desk sobre utilização do Sakai

- https://www.geminiodyssey.org/documents/...011
