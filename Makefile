bootJar:
	\
	./gradlew clean bootJar
snapshot:
	\
	./gradlew clean build
start:
	\
	./gradlew clean assemble && \
	docker-compose up -d --force-recreate
stop:
	\
	docker-compose down --rmi all
test:
	\
	./gradlew clean test
