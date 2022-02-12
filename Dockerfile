#Build our image on Base Image
FROM maven:3.8.4-openjdk-8-slim

#Create a directory for APP
RUN mkdir /home/app

#Copy files from current folder
COPY PPMTool/target/ppmtool-0.0.1-SNAPSHOT.jar /home/app/ppmtool-1.0.0-SNAPSHOT.jar
COPY . .

#Entrypoint
ENTRYPOINT ["java", "-jar", "/home/app/ppmtool-1.0.0-SNAPSHOT.jar"]