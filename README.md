SpringBoot-Mybatis-MqttPS
=========================
sample
------------
# 0. before you maintenance this project

## 0.1. Dependency (Learning curve)
* This project was written by [kotlin](https://kotlinlang.org/)
    - [KDoc](https://kotlinlang.org/docs/reference/kotlin-doc.html) - javadoc for kotlin
* [gradle](https://gradle.org/).[MultiModuleProject](https://guides.gradle.org/creating-multi-project-builds/)
    - [dokka](https://github.com/Kotlin/dokka) - to use KDoc
    - [kapt](http://kotlinlang.org/docs/reference/kapt.html) - annotationProcessor for kotlin
* [yml](http://yaml.org/) - to set option or mock up data
* [mysql](https://www.mysql.com/) - database
* [mybatis](http://www.mybatis.org/mybatis-3/) - to use mysql
* [MQTT](http://mqtt.org/).[Paho](https://www.eclipse.org/paho/) - to communicate with BLE scanner
* [MarKDown](https://www.markdownguide.org/getting-started) - if you want to update this document
    - [Cheat Sheet](https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet)
    - [korean introduction](https://gist.github.com/ihoneymon/652be052a0727ad59601)

# 1. :api

## 1.1. Dependency (Learning curve)

* [spring-boot](https://spring.io/projects/spring-boot)

## 1.2. Documentation

    Open endpoints require no Authentication.

### 1.2.1 Admin

Method|Endpoint|Explain
---|---|---
GET|[/admins](docs/admins/get.md)|관리자 정보 가져오기
POST|[/admins](docs/admins/post.md)|관리자 입력
DELETE|[/admins](docs/admins/delete.md)|관리자 삭제
GET|[/admins/data](docs/admins/data/get.md)|관리자 환경정보 가져오기

# 2. :core
    
## 2.1 Dependency (Learning curve)

# 3. :mqtt:publisher

    MQTT Publisher
    
## 3.1 Dependency (Learning curve)

* [shadowJar](https://github.com/johnrengelman/shadow) - to make a [fatjar](https://stackoverflow.com/questions/19150811/what-is-a-fat-jar)

## 3.2 YML Config

> option.yml
>   >Name           |Explanation    
---          |---          
LogLevel       |0 = Debug , 1 = Default, 2 = Mute
Domain         |just plain domain
Port           |broker's MQTT port
SSL            |true or false
UserName       |사용자 이름
Password       |사용자 비밀번호
LoopInterval   |데이터 전송 간격
LoopCount      |데이터 전송 반복 횟수
> here is example
>   > ```YAML
>   > option:
>   >   LogLevel: 0
>   >   Domain: your.domain
>   >   Port: 1883
>   >   SSL: false
>   >   UserName: root
>   >   Password: 1234
>   >   LoopInterval: 3000
>   >   LoopCount: 3
>   > ```

> data.yml 
>   > define individual topic and list up your data(hex byte array)
>   > ```YAML
>   > CMS_HUB/BIO_SIGNAL:
>   >   - a1b2c3d4e5f6
>   >   - ab,cd,11,22,33,44
>   > ```
> you can make some data with `,`. It will be removed when publish 
# 4. :matt:subscriber

    MQTT Subscriber
    
## 4.1 Dependency (Learning curve)

* [shadowJar](https://github.com/johnrengelman/shadow) - to make a [fatjar](https://stackoverflow.com/questions/19150811/what-is-a-fat-jar)
* [Apache Daemon](https://commons.apache.org/proper/commons-daemon/) - to run on background
    - [Procrun](https://commons.apache.org/proper/commons-daemon/procrun.html) - it makes window service from .jar
* [Window batch script](https://www.lesstif.com/pages/viewpage.action?pageId=17105830) - to change Procrun configuration file

## 4.2 YML Config

> option.yml
>   >Name           |Explanation    
---          |---
LogLevel       |0 = Debug , 1 = Default, 2 = Mute
Domain         |just plain domain
Port           |broker's MQTT port
SSL            |true or false
UserName       |사용자 이름
Password       |사용자 비밀번호
> here is example
>   > ```YAML
>   > option:
>   >   LogLevel: 0
>   >   Domain: your.domain
>   >   Port: 1883
>   >   SSL: false
>   >   UserName: root
>   >   Password: 1234
>   > ```

