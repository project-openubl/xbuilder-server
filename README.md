[![Build Status](https://travis-ci.org/project-xavier/xavier-integration.svg?branch=master)](https://travis-ci.org/project-xavier/xavier-integration)
[![codecov](https://codecov.io/gh/project-xavier/xavier-integration/branch/master/graph/badge.svg)](https://codecov.io/gh/project-xavier/xavier-integration)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=project-xavier_xavier-integration&metric=alert_status)](https://sonarcloud.io/dashboard?id=project-xavier_xavier-integration)

Fuse (on Spring Boot) to create a prototype to integrate JPA (with external DB), JMA (AMQ Broker), Drools (remote invocation), Rest APIs, Swagger.
# Installation

## OKD
1. install [OKD](https://www.okd.io/) **3.11** following [these instructions](https://github.com/openshift/origin/blob/v3.11.0/docs/cluster_up_down.md)
1. `oc cluster up`
1. `oc login -u system:admin`
1. `oc project openshift`

## Red Hat Registry Service Account
1. Set up and download an Openshift registry service account following the instructions from https://access.redhat.com/RegistryAuthentication#registry-service-accounts-for-shared-environments-4
1. `oc create -f <secret>.yaml` (`<secret>` is the name of the downloaded Openshift registry service account)
1. `oc secrets link default <secret> --for=pull`
1. `oc secrets link builder <secret> --for=pull`

## Red Hat Decision Manager
Instructions from https://access.redhat.com/documentation/en-us/red_hat_decision_manager/7.3/html-single/deploying_a_red_hat_decision_manager_authoring_or_managed_server_environment_on_red_hat_openshift_container_platform/index#dm-openshift-prepare-con
1. Download from https://access.redhat.com/jbossnetwork/restricted/listSoftware.html?downloadType=distributions&product=rhdm&productChanged=yes and unzip
1. `oc create -f ./Downloads/rhdm-7.3-openshift-templates/rhdm73-image-streams.yaml`
1. `oc import-image rhdm73-decisioncentral-openshift:1.0`
1. `oc import-image rhdm73-kieserver-openshift:1.0`

## Red Hat AMQ Broker
Instructions from https://access.redhat.com/documentation/en-us/red_hat_amq/7.3/html-single/deploying_amq_broker_on_openshift_container_platform/index#installing-broker-ocp_broker-ocp
1. `oc replace --force  -f https://raw.githubusercontent.com/jboss-container-images/jboss-amq-7-broker-openshift-image/73-7.3.0.GA/amq-broker-7-image-streams.yaml`
1. `oc import-image amq-broker-7/amq-broker-73-openshift --from=registry.redhat.io/amq-broker-7/amq-broker-73-openshift --confirm`
1. `for template in amq-broker-73-basic.yaml amq-broker-73-ssl.yaml amq-broker-73-custom.yaml amq-broker-73-persistence.yaml amq-broker-73-persistence-ssl.yaml amq-broker-73-persistence-clustered.yaml amq-broker-73-persistence-clustered-ssl.yaml;  do  oc replace --force -f https://raw.githubusercontent.com/jboss-container-images/jboss-amq-7-broker-openshift-image/73-7.3.0.GA/templates/${template};  done`

## Red Hat Fuse
Instructions from https://access.redhat.com/documentation/en-us/red_hat_fuse/7.3/html-single/fuse_on_openshift_guide/index#get-started-admin-install
1. `BASEURL=https://raw.githubusercontent.com/jboss-fuse/application-templates/application-templates-2.1.fuse-730065-redhat-00002`
1. `oc create -n openshift -f ${BASEURL}/fis-image-streams.json`
1. `for template in eap-camel-amq-template.json  eap-camel-cdi-template.json  eap-camel-cxf-jaxrs-template.json  eap-camel-cxf-jaxws-template.json  eap-camel-jpa-template.json  karaf-camel-amq-template.json  karaf-camel-log-template.json  karaf-camel-rest-sql-template.json  karaf-cxf-rest-template.json  spring-boot-camel-amq-template.json  spring-boot-camel-config-template.json  spring-boot-camel-drools-template.json  spring-boot-camel-infinispan-template.json  spring-boot-camel-rest-sql-template.json  spring-boot-camel-teiid-template.json  spring-boot-camel-template.json  spring-boot-camel-xa-template.json  spring-boot-camel-xml-template.json  spring-boot-cxf-jaxrs-template.json  spring-boot-cxf-jaxws-template.json ;  do  oc create -n openshift -f  https://raw.githubusercontent.com/jboss-fuse/application-templates/application-templates-2.1.fuse-730065-redhat-00002/quickstarts/${template};  done`

# Deployment

## OCP templates
### Using OCP Web UI
1. click on `Create Project` button to create a new project
1. use `migration-analytics` as value for the name
1. once the project has been created and added to the list of the available project, click on `migration-analytics`
1. from the `migration-analytics` Overview page, click on `Import YAML / JSON` button
1. copy and paste the content of the [analytics_template.json](src/main/resources/okd/analytics_template.json) (using the `Raw` button will let you have the plain text version of this file)
1. click on `Create` button
1. check that `Process the template` is selected (no need to select `Save template`)
1. click on `Continue` button
1. click on `Create` button (No need to change the form values unless the user wants to customize them)
### Using `oc` command line tool
1. `oc login -u developer`
1. `oc new-project migration-analytics`
1. `oc create -f <secret>.yaml`
1. `oc secrets link default <secret> --for=pull`
1. `oc secrets link builder <secret> --for=pull`
1. `oc process -f https://raw.githubusercontent.com/project-xavier/xavier-integration/master/src/main/resources/okd/analytics_template.json| oc create -f -`

## Decision Manager
1. go to Application -> Routes page and click on the URL in the `Hostname` column beside the `myapp-rhdmcentr` service
1. login with `adminUser`-`SxNhwF2!`
1. click on `Design` link
1. click on `Import Project` button
1. in the `Repository URL` field paste `https://github.com/project-xavier/xavier-analytics.git`
1. select `sample-analytics` box and click the `OK` button on the upper right side
1. once the import has finished, click the `Build & Install` button from the upper right `Build` menu
1. once the build has been successfully done, click on the `Deploy` button

# Manage

## PostgreSQL
1. Go to Resources -> Secrets page and select `postgresql` secret for the list of the secrets
1. Select `Reveal Secret` link to get the `database-name` value
1. Go to PostgreSQL pod's `Terminal` tab to log in doing `psql <database-name>`
1. To get the report entries persisted execute `select * from report_data_model;`
1. To DELETE ALL the report entries execute `truncate table report_data_model;`
## AMQ Broker
AMQ Web Console
## Camel routes
To enable the `DEBUG` level for logging, please add the environment variable `logging.level.org` (or whatever package you want) with value `DEBUG` to the the `analytics-integration` deployment configuration. 
# Undeploy
1. `oc delete all,pvc,secrets -l application=migration-analytics -n migration-analytics`

# Sonar
1.  https://sonarcloud.io/dashboard?id=project-xavier_xavier-integration
2. mvn clean verify -Psonar -Dsonar.login={{token generated for the user on SonarCloud}}

# AWS S3
In order to test AWS S3, we can download and install [aws-cli command](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-install.html#install-tool-pip)  
In order to interact as admin with the S3 bucket we can use the [S3Api](https://docs.aws.amazon.com/cli/latest/reference/s3api/)  
The documentation for interacting as users is [S3](https://docs.aws.amazon.com/cli/latest/userguide/cli-services-s3-commands.html)  
From the Camel perspective we have the test class `MainRouteBuilder_S3Test` that we can use to test locally against AWS S3 servers , replacing the credentials headers.  
Few mentions :
 * we need to add the HTTP headers in order to download the file from the API call
 * we need to stub (disable) the aws-s3 component as it has eager start and then it will crash as we are not having the credentials in the Tests . This is fixed on Camel 3 on the DefaultEndpoint class with its `lazyProducerStart` ( but this will be available on Fuse 8)
 * we need to specify `deleteAfterRead=false` in the call , if not the resource will be deleted after being read
 * same concept but different names for Camel consistency : KEY (upload) / FILENAME (download)  
 
Snippets of calls :
* Configure aws : `aws configure` with your credentials and region=`us-east-1` and bucket=`xavier-dev`  
* List files in a bucket : `aws s3 ls s3://xavier-dev --human`
* List buckets : `aws s3 ls`
* Upload a file : `aws s3 cp cfme_inventory_0.json s3://xavier-dev`
* Download a file : `aws s3 cp s3://xavier-dev/cfme_inventory_0.json cfme_inventory_0.json`
* Remove files in a bucket : `aws s3 rm s3://xavier-dev/ --recursive`
* See status of encription on a bucket : `aws s3api get-bucket-encryption --bucket xavier-dev`
* Enable encription on a bucket : `aws s3api put-bucket-encryption --bucket xavier-dev --server-side-encryption-configuration '{"Rules": [{"ApplyServerSideEncryptionByDefault": {"SSEAlgorithm": "AES256"}}]}' `  

# References

