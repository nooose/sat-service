.PHONY: up down

up:
	cd docker && docker-compose up -d
down:
	cd docker && docker-compose down

