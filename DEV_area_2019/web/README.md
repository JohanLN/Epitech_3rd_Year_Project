# web



## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).

### Objectif du projet
L’AREA est un projet de gestion de la vie numérique en temps réel.Les utilisateurs de l’Area peuvent créer un compte à l’AREA via l’application web ou l’application mobile. L’utilisateur, une fois inscrit sur le site, pourra souscrire à des services:Facebook,Google,Twitter,qui offriront différentes actions et différentes réactions.L’acronyme AREA signifie “ActionREAction”.Les utilisateurs peuvent alors lier des actions à des réactions.Les réactions se déclenchent alors quand l'événement de l’action est activé.

### Partie Web 
Une Application web en VueJS via laquelle les utilisateurs peuvent s’inscrire,se connecter,souscrire à des services et lier des actions à des réactions. L’application web doit seulement s’occuper de l’affichage en appelant l’API fournie par le serveur,et ne doit pas contenir de logique métier.

### Authentification
- Register : Utilisation de la méthode Fetch avec un Post sur api/user/ avec comme body l'user, le password et un mail
- Login : Utilisation de la méthode Fetch avec un Post sur api/user/login avec comme body l'user et le password
- Services : Utilisation du sdk OAuth.io qui permet d'ouvrir des popup et se connecter au différents services.

### Action
-RegisterServices : Utilisation de la méthode Fetch avec un Post sur api/RegisteredServices avec comme body le nom du services et son accessToken
-RegisterAction : Utilisation de la méthode Fetch avec un Post sur api/Actions avec comme body "{
 	"name":"GotMessage", 
    "Reaction":{
    	"name":"WillSendMessage", 
        "params":{
            "target":"id",
            "content":"msg"
        }
    }
}"