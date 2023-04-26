## introduction

# Contexte du projet
Le projet d’intégration s’inscrit dans le cadre des projets de troisième année de la Licence Informatique du département Sciences et techniques de l’Université de Cergy-Pontoise. Le projet a pour but
de simuler un contrôle de trafic aérien.Le travail est réaliser par SOILIHI Djouhoudi, MOUSSA Bilel,
MALKI Mohammed Ilyes et YILMAZ Celil.

# Objectif du projet
L’objectif est de créer un logiciel en temps réel pour simuler les opérations de l’ATC (Air Trafic
Control). Le logiciel fournira une interface utilisateur simplifiée en 2D pour visualiser les avions et
gérer les décollages et les atterrissages, ainsi que les situations d’urgence. L’objectif principal est de
prévenir les collisions entre les avions et les obstacles, tout en accélérant et en ordonnant la circulation
aérienne.
Lors de ce projet nous devrons apprendre à utiliser et implémenter le multithreading pour la gestion
de chaque avion afin de gérer chaque événement cité précédemment. De plus on devra gérer l’avancement d’un projet en groupe et donc effectuer et compléter un cahier des charges de manière coordonnée
et efficace.

# Fonctionnement général du logiciel
Le logiciel de simulation de contrôle aérien utilise une interface graphique pour permettre à l’utilisateur de contrôler des avions et de les faire voler dans un environnement en 2D. Lorsque le programme
démarre, il charge une carte d’élévation qui est utilisée pour générer le terrain sur lequel les avions
vont évoluer. Cette carte d’élévation est stockée sous forme d’un tableau à deux dimensions d’entiers,
où chaque entier représente l’altitude d’un bloc de terrain.
L’utilisateur peut ajouter des avions en spécifiant leur position de départ et leur aéroport de destination. Le logiciel crée alors un processus pour chaque avion, qui est responsable de faire voler l’avion
jusqu’à sa destination. Chaque processus effectue les calculs de vol en parallèle avec les autres processus.
Les processus d’avion utilisent des algorithmes de vol pour déterminer les trajectoires des avions
en fonction de leur altitude, de leur vitesse et de leur cap. Les processus mettent également à jour la
position des avions sur la carte à chaque itération, en utilisant l’algorithme de collision pour détecter les
éventuelles collisions entre les avions. Si une collision est détectée, le processus de l’avion correspondant
ajuste la trajectoire de l’avion pour éviter la collision.
Lorsqu’un avion atteint son aéroport de destination, il se pose et est retiré de la simulation. L’utilisateur peut ajouter de nouveaux avions à tout moment et les avions existants continuent de voler
jusqu’à leur destination. La simulation continue jusqu’à ce que l’utilisateur décide de la stopper.




