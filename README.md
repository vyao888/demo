# Running the Spring Application using Docker

### Build the docker image

````````
docker build -t springboot-app .

  Sending build context to Docker daemon  18.06MB
  Step 1/4 : FROM openjdk:11
  11: Pulling from library/openjdk
  0ecb575e629c: Pull complete 
  7467d1831b69: Pull complete 
  feab2c490a3c: Pull complete 
  f15a0f46f8c3: Pull complete 
  26cb1dfcbebb: Pull complete 
  242c5446d23f: Pull complete 
  f22708c7c9c1: Pull complete 
  Digest: sha256:3805f5303af58ebfee1d2f5cd5a897e97409e48398144afc2233f7b778337017
  Status: Downloaded newer image for openjdk:11
   ---> 82e02728b3fd
  Step 2/4 : ADD ./target/demo-0.0.1-SNAPSHOT.jar /usr/src/demo-0.0.1-SNAPSHOT.jar
   ---> 3d56d795bd10
  Step 3/4 : WORKDIR usr/src
   ---> Running in c52db1dc742e
  Removing intermediate container c52db1dc742e
   ---> c6a66c5f936c
  Step 4/4 : ENTRYPOINT ["java","-jar", "demo-0.0.1-SNAPSHOT.jar"]
   ---> Running in 220b1690f4f2
  Removing intermediate container 220b1690f4f2
   ---> 67a42dfdc628
  Successfully built 67a42dfdc628
  Successfully tagged springboot-app:latest

docker images

  REPOSITORY                       TAG                  IMAGE ID       CREATED              SIZE
  springboot-app                   latest               67a42dfdc628   About a minute ago   665MB

````````

### Run Spring Application

``````
docker run -d -p 8088:8181 springboot-app

  d9d3cf6269f1c7d4900c6dc22f994dbfa9722a70d8d1d518f5f9244ccfefe011

docker ps
  CONTAINER ID   IMAGE                                               COMMAND                  CREATED         STATUS                 PORTS                    NAMES
  d9d3cf6269f1   springboot-app                                      "java -jar demo-0.0.…"   2 minutes ago   Up 2 minutes           0.0.0.0:8088->8181/tcp   dreamy_dijkst

``````

### Check Spring Application

The web application should be deployed and the web page can be accessed in "localhost:8088"

### Stop Spring Application 
```
docker stop d9d3cf6269f1
  d9d3cf6269f1
```

### Running the Spring Application using Docker

### Build the docker image

````````
  docker build --no-cache -t demoapp -f Dockfile2 .
  
  docker images
  REPOSITORY                       TAG                  IMAGE ID       CREATED          SIZE
  demoapp                          latest               cf8efb1b7b4d   10 minutes ago   694MB

````````

### Run Spring Application Test

````
docker run -it --rm demoapp /bin/sh

# mvn test
  [INFO] Scanning for projects...
  Downloading from central: https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-starter-parent/2.4.3/spring-boot-starter-parent-2.4.3.pom
  Downloaded from central: https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-starter-parent/2.4.3/spring-boot-starter-parent-2.4.3.pom (8.6 kB at 12 kB/s)
  ...
  [INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.868 s - in com.example.demo.controller.TransactionControllerTest
  2021-02-20 16:45:11.567  INFO 78 --- [extShutdownHook] o.s.s.concurrent.ThreadPoolTaskExecutor  : Shutting down ExecutorService 'applicationTaskExecutor'
  2021-02-20 16:45:11.631  INFO 78 --- [extShutdownHook] o.s.s.concurrent.ThreadPoolTaskExecutor  : Shutting down ExecutorService 'applicationTaskExecutor'
  [INFO] 
  [INFO] Results:
  [INFO] 
  [INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
  [INFO] 
  [INFO] ------------------------------------------------------------------------
  [INFO] BUILD SUCCESS
  [INFO] ------------------------------------------------------------------------
    
# exit

````

### Notes

The above is MAC based. It should be applicable to other operating systems



