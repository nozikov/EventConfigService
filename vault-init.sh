#!/bin/bash
# Script to initialize Vault and add secrets for the application

# Set variables
VAULT_ADDR="http://localhost:8200"
VAULT_TOKEN="root"
APP_NAME="event-config-service"

# Export variables for vault CLI
export VAULT_ADDR
export VAULT_TOKEN

echo "Initializing Vault with secrets for $APP_NAME..."

# Enable KV secrets engine version 2 if not already enabled
vault secrets enable -version=2 -path=secret kv || echo "KV secrets engine already enabled"

# Database credentials
echo "Adding database credentials..."
vault kv put secret/$APP_NAME/database \
  username="postgres" \
  password="secure_db_password"

# Kafka credentials (if needed)
echo "Adding Kafka credentials..."
vault kv put secret/$APP_NAME/kafka \
  username="kafka_user" \
  password="secure_kafka_password"

# Webhook signing key
echo "Adding webhook signing key..."
vault kv put secret/$APP_NAME/webhook \
  signing-key="$(openssl rand -base64 32)"

echo "Vault initialization complete!"
echo "To view the secrets:"
echo "vault kv get secret/$APP_NAME/database"
echo "vault kv get secret/$APP_NAME/kafka"
echo "vault kv get secret/$APP_NAME/webhook"

echo ""
echo "Note: In a production environment, you should:"
echo "1. Use a more secure authentication method than the root token"
echo "2. Enable audit logging"
echo "3. Configure proper access policies"
echo "4. Set up high availability and disaster recovery"