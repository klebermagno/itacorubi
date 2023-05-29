# To run this project locally

```cli
mvn exec:java -Dexec.mainClass=com.itacorubi.data.collection.StockDataPipeline -Pdirect-runner 
```

# Commit trigger
gcloud beta builds triggers create github \
  --repo-name=itacorubi \
  --repo-owner=klebermagno \
  --branch-pattern="^master$" \
  --build-config="cloudbuild_commit.yaml"

# Tag trigger
gcloud beta builds triggers create github \
  --repo-name=itacorubi \
  --repo-owner=klebermagno \
  --tag-pattern="^v\d+\.\d+\.\d+$" \
  --build-config="cloudbuild_tag.yaml"

# To generate a project template
mvn archetype:generate \
     -DarchetypeGroupId=org.apache.beam \
     -DarchetypeArtifactId=beam-sdks-java-maven-archetypes-examples \
     -DarchetypeVersion=2.33.0 \
     -DgroupId=com.itacorubi.data.collection \
     -DartifactId=data-collection \
     -Dversion="0.1" \
     -DinteractiveMode=false


