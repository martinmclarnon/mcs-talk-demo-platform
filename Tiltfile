# Tiltfile
# Ports:
# Zookeeper: 2181
# kafka: 9092
# akhq ui: 8080
# marketing db: 5432
# store db: 5433
# mongo db: 27017
# api bff blog: 8081
# api bff web inventory: 8082
# web store frontend: 8083
# create-order-command: 8084
# create-order-audit: 8085
# pg admin ui: 8090


# Marketing: 9080 and above
# store: 10080 and above

# Function to create namespace for all worker nodes
def create_namespace_service():
    k8s_yaml('./local-cicd-manifests/demo-namespace.yaml')

# Function to build and deploy: zookeeper service
def zookeeper_service():
    # Specify the Kubernetes manifest for the deployment
    k8s_yaml('./local-cicd-manifests/demo-zookeeper.yaml')
    # Assign port
    k8s_resource('demo-zookeeper',
                 port_forwards=['2181:2181'],
                 labels="message-broker")

# Function to build and deploy: kafka service
def kafka_service():
    # Specify the Kubernetes manifest for the deployment
    k8s_yaml('./local-cicd-manifests/demo-kafka.yaml')
    # Assign port
    k8s_resource('demo-kafka',
                 port_forwards=['9092:9092'],
                 resource_deps=['demo-zookeeper'],
                 labels="message-broker")

# Function to build and deploy: akhq service
def akhq_service():
    # Specify the Kubernetes manifest for the deployment
    k8s_yaml('./local-cicd-manifests/demo-akhq.yaml')
    # Assign port
    k8s_resource('demo-akhq',
                 port_forwards=['8080:8080'],
                 resource_deps=['demo-kafka'],
                 labels="message-broker")

# Function to build and deploy: Database service
def marketing_db_service():
    # Build from Dockerfile
    docker_build('demo-marketing-db',
                 context='./src/marketing/persistent-storage/db',
                 dockerfile='./src/marketing/persistent-storage/db/Dockerfile')
    # Specify the Kubernetes manifest for the API deployment
    k8s_yaml('./local-cicd-manifests/demo-marketing-db.yaml')
    # Assign port
    k8s_resource('demo-marketing-db',
                 port_forwards=['5432:5432'],
                 labels="persistent-storage")

# Function to build and deploy: liquibase update for db service
def marketing_db_change_management_service():
    # Build from Dockerfile
    docker_build('demo-marketing-db-change-management',
                 context='./src/marketing/persistent-storage/db-change-management',
                 dockerfile='./src/marketing/persistent-storage/db-change-management/Dockerfile')
    # Specify the Kubernetes manifest for the deployment
    k8s_yaml('./local-cicd-manifests/demo-marketing-db-change-management.yaml')
    # Assign port
    k8s_resource('demo-marketing-db-change-management',
                 port_forwards=['5430:5432'],
                 resource_deps=['demo-marketing-db'],
                 labels="persistent-storage")

# Function to build and deploy: Database service
def store_db_service():
    # Build from Dockerfile
    docker_build('demo-store-db',
                 context='./src/store/persistent-storage/db',
                 dockerfile='./src/store/persistent-storage/db/Dockerfile')
    # Specify the Kubernetes manifest for the API deployment
    k8s_yaml('./local-cicd-manifests/demo-store-db.yaml')
    # Assign port
    k8s_resource('demo-store-db',
                 port_forwards=['5433:5432'],
                 labels="persistent-storage")

# Function to build and deploy: liquibase update for db service
def store_db_change_management_service():
    # Build from Dockerfile
    docker_build('demo-store-db-change-management',
                 context='./src/store/persistent-storage/db-change-management',
                 dockerfile='./src/store/persistent-storage/db-change-management/Dockerfile')
    # Specify the Kubernetes manifest for the deployment
    k8s_yaml('./local-cicd-manifests/demo-store-db-change-management.yaml')
    # Assign port
    k8s_resource('demo-store-db-change-management',
                 port_forwards=['5431:5432'],
                 resource_deps=['demo-store-db'],
                 labels="persistent-storage")

def mongodb_service():
    # Specify the Kubernetes manifest for the deployment
    k8s_yaml('./local-cicd-manifests/demo-mongodb.yaml')
    # Assign port
    k8s_resource('demo-mongodb',
                 port_forwards=['27017:27017'],
                 labels="persistent-storage")

# # Function to build and deploy:BFF service
def bff_web_blog_service():
    # Build from Dockerfile
    docker_build('demo-bff-web-blog',
                 context='./src/marketing/bff-blog',
                 dockerfile='./src/marketing/bff-blog/Dockerfile')
    # Specify the Kubernetes manifest for the API deployment
    k8s_yaml('./local-cicd-manifests/demo-bff-web-blog.yaml')
    # Assign port
    k8s_resource('demo-bff-web-blog',
                 port_forwards=['8081:8080','5005:5005'],
                 resource_deps=['demo-marketing-db'],
                 labels="service",
                 trigger_mode=TRIGGER_MODE_MANUAL)

# Function to build and deploy:API service
def bff_web_inventory_service():
    # Build from Dockerfile
    docker_build('demo-bff-web-inventory',
                 context='./src/store/bff-inventory',
                 dockerfile='./src/store/bff-inventory/Dockerfile')
    # Specify the Kubernetes manifest for the API deployment
    k8s_yaml('./local-cicd-manifests/demo-bff-web-inventory.yaml')
    # Assign port
    k8s_resource('demo-bff-web-inventory',
                 port_forwards=['8082:8080','5006:5006'],
                 resource_deps=['demo-store-db'],
                 labels="service",
                 trigger_mode=TRIGGER_MODE_MANUAL)

# # Function to build and deploy: Frontend service
def store_frontend_service():
    # Build from Dockerfile
    docker_build('demo-store-frontend',
                 context='./src/store/frontend',
                 dockerfile='./src/store/frontend/Dockerfile')
    # Specify the Kubernetes manifest for the Frontend deployment
    k8s_yaml('./local-cicd-manifests/demo-store-frontend.yaml')
    # Assign port
    k8s_resource('demo-store-frontend',
                 port_forwards=['8083:80'],
                 resource_deps=['demo-bff-web-inventory'],
                 labels="web",
                 trigger_mode=TRIGGER_MODE_MANUAL)

# Function to build and deploy:pgAdmin service
def pgadmin_service():
    # Build from Dockerfile
    # docker_build('pgadmin', context='./local-utilities/pgadmin/', dockerfile='./local-utilities/pgadmin/Dockerfile')
    # Specify the Kubernetes manifest for the pgAdmin deployment
    k8s_yaml('./local-cicd-manifests/demo-pgadmin.yaml')
    # Assign port
    k8s_resource('demo-pgadmin',
                 port_forwards=['8090:80'],
                 resource_deps=['demo-frontend'],
                 labels="utility")

# Function to build and deploy: producer service
def create_order_command_service():
    # Build from Dockerfile
    docker_build('demo-create-order-command', context='./src/store/create-order-command', dockerfile='./src/store/create-order-command/Dockerfile')
    # Specify the Kubernetes manifest for the deployment
    k8s_yaml('./local-cicd-manifests/demo-create-order-command.yaml')
    # Assign port
    k8s_resource('demo-create-order-command',
                 port_forwards='8084:8080',
                 resource_deps=['demo-kafka'],
                 trigger_mode=TRIGGER_MODE_MANUAL,
                 labels="service")

# Function to build and deploy: consumer service
def create_order_audit_service():
    # Build from Dockerfile
    docker_build('demo-create-order-audit', context='./src/store/create-order-audit', dockerfile='./src/store/create-order-audit/Dockerfile')
    # Specify the Kubernetes manifest for the deployment
    k8s_yaml('./local-cicd-manifests/demo-create-order-audit.yaml')
    # Assign port
    k8s_resource('demo-create-order-audit',
                 port_forwards='8085:8080',
                 resource_deps=['demo-kafka','demo-create-order-command'],
                 trigger_mode=TRIGGER_MODE_MANUAL,
                 labels="service")

create_namespace_service()
zookeeper_service()
kafka_service()
akhq_service()
mongodb_service()
marketing_db_service()
marketing_db_change_management_service()
store_db_service()
store_db_change_management_service()
bff_web_blog_service()
bff_web_inventory_service()
store_frontend_service()
pgadmin_service()
create_order_command_service()
create_order_audit_service()