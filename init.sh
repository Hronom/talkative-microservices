docker-compose exec config01 sh -c "mongo --port 28017 < /scripts/init-configserver.js"
docker-compose exec config02 sh -c "mongo --port 28018 < /scripts/init-configserver.js"
docker-compose exec config03 sh -c "mongo --port 28019 < /scripts/init-configserver.js"
docker-compose exec shard01a sh -c "mongo --port 29017 < /scripts/init-shard01.js"
docker-compose exec shard02a sh -c "mongo --port 29018 < /scripts/init-shard02.js"
docker-compose exec shard03a sh -c "mongo --port 29019 < /scripts/init-shard03.js"
sleep 20
docker-compose exec router sh -c "mongo --port 27017 < /scripts/init-router.js"