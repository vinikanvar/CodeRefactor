# CodeRefactor

It takes a monolithic application and its clustering meta data, and:
1. Creates clusters with the meta data recommendation.
2. Computes the movable subgraphs and API candidates.
3. Relocates the subgraphs based on the isolation information and reduces cross-cluster dependencies.
4. Refactors microservices by (a) exposing endpoints, (b) converting method calls to REST API calls, and (c) communicates data using id-passing approach between microservices via APIs.
5. Results_id-passing: Contains the deployable microservices for five applications (DayTrader, Acmeair, PetClinic, PlantsByWebsphere, and Mayocat). The Swagger files and refactored microservices are in ui/data in the respective folders.
6. Survey.pdf: Results of our survey conducted to study the quality of the refactored microservices and their benefits to the developers.

