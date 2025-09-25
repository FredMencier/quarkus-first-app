docker run \
    -it \
    --rm \
    -v /Users/fredericmencier/Projects/quarkus-first-app/hyperfoil:/benchmarks:Z \
    -v /Users/fredericmencier/Projects/quarkus-first-app/hyperfoil/reports:/tmp/reports:Z \
    quay.io/hyperfoil/hyperfoil \
    run -o /tmp/reports /benchmarks/personsBenchmark.yml