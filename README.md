Application developped by:
    - Sacha MEMMI
    - Mathieu
    - Quentin
    - Pierre-Octave

To launch this application locally you have to do the following commands:
    Create the network on docker
        docker network create -d bridge yourNetworkName
    Run database docker image with the wanted options
        go to the directory databases in server 
        docker run --name yourDockerDatabaseName \
        -p 3306:3306 \
        --network yourNetworkName \
        -v path/to/dump/file:/docker-entrypoint-initdb.d \
        -v /directory/of/the/location/you/want/the/data/to/be/saved \ 
        -e MYSQL_ROOT=UserRoot \
        -e MYSQL_USER=YourUsername \
        -e MYSQL_PASSWORD=UsernamePASSWORD \
        -e MYSQL_DATABASE=NameOfDatabase \
        -e TZ=Europe/Paris mysql
    Build the image of back-end server
        docker build -t myImageBackName ./server/safetyline
    Run the back with the good network
        docker run --name myContainerName -p 8020:8020 \
        --network yourNetworkName \
        -e SPRING_DATASOURCE_URL=yourServerURL \
                                example for local purpose = 'jdbc:mysql://yourDockerDatabaseName:3306/NameOfDatabase?
                                                    autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true' \
        -e SPRING_DATASOURCE_NAME=NameOfDatabase \
        -e SPRING_DATASOURCE_USERNAME=YourUsername \
        -e SPRING_DATASOURCE_PASSWORD=UsernamePASSWORD \
        -e SERVER_PORT=8020 \
        -e SPRING_DATASOURCE_DRIVER-CLASS-NAME=com.mysql.cj.jdbc.Driver myImageBackName
    Front Part
    Build the image of front-server
        Go to client/SB
        docker build -t nameOfYourImage .
        docker run --network=yourNetworkName -it --name nameOfYourContainerFront -p 4200:80 NameOfYourImage
