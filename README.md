# ****Rick-and-morty**** #

## 🚀 Quickstart
1. Fork this repository
2. Copy link of project
3. Create new project from Version Control
4. Download image with Postgres 15-Alpine from Docker
'docker pull postgres:15-alpine'

5. Download image with Project Rick and Morty from Docker
'docker pull verbanov/rick-and-morty'

8. Run project
'docker-compose up'

## 🚀 Features
1. Synchronization from API every minute
2. Use port:8081 for requests and port:5433 for database
``` Get random character from the database
    GET: /characters/random
    Example: Example: http://localhost:8081/characters/random
```    
``` Get character from the database wgich contains symbols 
    GET: /characters/by-name
    Example: http://localhost:8081/characters/by-name?name=m
