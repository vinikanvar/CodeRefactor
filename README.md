# CodeRefactor

It takes a monolithic application and its clustering meta data, and:
1. Creates clusters with the meta data recommendation.
2. Computes the movable subgraphs and API candidates.
3. Relocates the subgraphs based on the isolation information and reduces cross-cluster dependencies.
