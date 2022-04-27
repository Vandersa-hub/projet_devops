# Projet_devOPS : PyToJava for Pandas

[![Build and Tests](https://github.com/Vandersa-hub/projet_devops/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/Vandersa-hub/projet_devops/actions/workflows/maven.yml)
[![pages-build-deployment](https://github.com/Vandersa-hub/projet_devops/actions/workflows/pages/pages-build-deployment/badge.svg?branch=gh-pages)](https://github.com/Vandersa-hub/projet_devops/actions/workflows/pages/pages-build-deployment)
[![Deployment](https://github.com/Vandersa-hub/projet_devops/actions/workflows/deployment.yml/badge.svg?branch=master)](https://github.com/Vandersa-hub/projet_devops/actions/workflows/deployment.yml)

### Description et Objectif

Ce dépôt fait l'objet d'un projet universitaire ayant pour objectif de développer une version java de la bibliothèque Pandas développée en python, et ce, en utilisant la technique de déploiement qui nous semble la mieux adaptée.

### Fonctionnalités implémentées

#### Constructeur

Pour créer un DataFrame, deux choix s'offrent à vous :
Soit vous utilisez une instance de la classe CoupleLabelData comportant une chaîne de caractères (titre de la colonne) et un ArrayList (contenu de cette colonne).
Soit vous utilisez en paramètre un fichier CSV parsé correctement pour pouvoir être transformé en DataFrame, soit en multiples CoupleLabelData.


#### Affichage

Pour l'affichage, nous nous sommes inspiré de la documentation python notamment pour le format. 
Pour l'appliquer nous avons faire le choix de ne renvoyer qu'une chaine de caractère formattée plutôt que de définir nous même une façon de l'afficher (même si un sysout aurait suffit), nous voulions laisser la possibilité aux utilisateurs d'afficher cette chaine selon leurs besoins.
Afin de pour tester les différents types d'affichages : par défaut, en partant du début ou de la fin il nous fallait donc un format fixe testable.
Nous avons tenté de mettre en place une approche TDD(Test Driven Development) pour implémenter cette fonctionnalité.

#### Sélection


#### Statistique

Nous avons 3 statistiques disponibles :
mean qui renvoi la moyenne de la colonne selectionnée
min et max qui donnent respectivement le minimum et le maximum de la colonne selectionnée

Chacune de ces statistiques nécéssitent le label de la colonne en question, null est retourné si la colonne ne possède pas un type adéquat.


### Utilisation des technologies

Outil de couverture de code : Jacoco
Intégration continue : Maven
Tests unitaires : JUnit
Validation des tests : Github CI

### Méthodologie du workflow
On a suivi le Workflow :
- La branche master de notre répertoire doit toujours être déployable
- On utilisera le concept de feature branch pour les développements annexes.
- Il est impossible de push directement sur le master : il est nécessaire de passer par une pull request qui sera vérifiée par un autre membre de l'équipe de développement. Pour vérifier que tous les tests sur le code sont passés avant de merge, on utilisera un mvn package qui applique durant son cycle de vie la commande mvn test. Le merge sur la branche master sera suivi d'un déploiement sur un site "gh-page" accompagné d'un déploiement des dépendances du projet.
- Les merges et pulls requests seront effectuées régulièrement.

### Image Docker
Notre image Docker lance notre programme avec un fichier test défini. On aurait aimé que notre image Docker soit générée automatiquement par le service Docker Hub automated build  lors d'un merge sur la branche master mais cela est payant.

### FeedBack
Ce que nous pouvons dire a propos de cette méthodologie de travail, c'est principalement que l'application du déploiement, des tests, des pull request et des relectures,
nous incite à mettre en place de bonnes pratiques de développement. En effet, il nous semble que, les retours très rapide du déploiement continu, ainsi que les informations données par le coverage et le fait que notre code doit être facile à lire car d'autres développeurs vont passer derrière nous, favorise la bonne qualité du code et du code.  
Nous trouvons aussi que les outils fournit par github notamment pour faire des actions sont très bien intégré et très pratiques pour gagner du temps en général.