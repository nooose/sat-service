.PHONY: up down web-install web-up web-down

up:
	cd docker && docker-compose up -d
down:
	cd docker && docker-compose down

web-install:
	cd frontend && yarn install
web-up:
	cd frontend && yarn run dev &
web-down:
	kill -9 $(lsof -t -i :3000)
