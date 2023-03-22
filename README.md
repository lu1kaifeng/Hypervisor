
## Hypervisor
<p align="center">
<img width="200" height="200" src="https://github.com/HENU-Shabi/Hypervisor/blob/master/readme_pic.png" alt="Logo"/>
</p>
<p align="center">A mock university management system based on facial recognition and student engagement recognition  using deep learning.</p>
<p align="center"><strong>This project is built for China Collegiate Computing Contest 2020.</strong></p>

 two neural network backends are needed,they are:
 

 - [OpenFace docker image with customized API](https://hub.docker.com/repository/docker/lu1kaifeng/openface-final)
 - [docker image of the student engagement recognition NN with customized API](https://hub.docker.com/repository/docker/lu1kaifeng/engagement-recognition-final)
 
 Associated projects:
 - [OpenFace](http://cmusatyalab.github.io/openface/)
 - [Automatic Recognition of Student Engagement using Deep Learning and Facial Expression](https://github.com/omidmnezami/Engagement-Recognition)

 Redis and MySQL as well as [MinIO](https://min.io/)(object storage compatible with Amazon S3) is needed

please modify the application.properties accordingly:

```yaml
faceNet.url=##OpenFace docker API url

engagement.url=##student engagement NN docker API url

server.port=2233

server.address=0.0.0.0

## Spring DATASOURCE (DataSourceAutoConfiguration & DataSourceProperties)

spring.datasource.url=

spring.datasource.username=

spring.datasource.password=

spring.jpa.properties.hibernate.globally_quoted_identifiers=true

## Hibernate Properties

# The SQL dialect makes Hibernate generate better SQL for the chosen database

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect

# Hibernate ddl auto (create, create-drop, validate, update)

spring.jpa.hibernate.ddl-auto=update

spring.content.s3.bucket=##MinIO bucket

spring.content.s3.host=##MinIO host

spring.content.s3.accessKey=##MinIO accessKey

spring.content.s3.secretKey=##MinIO secretKey

redis.host.name=

redis.port=

distance.threshold=##face validation threshold(more info on https://cmusatyalab.github.io/openface/)

engagement.detection.interval.inMilli=1000

course.event.interval.inMilli=60000

event.time.period.inMinutes=5

lateArrival.threshold=20

HMAC256.secret=## JWT secret
```
