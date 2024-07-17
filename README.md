# Genie Chat Bot - Language Model Service

## Project Description
Genie Chat Bot is an AI-powered chatbot system providing instant lending-related query resolution. It supports multiple concurrent user sessions with real-time responses and comprehensive conversation history storage. The system is built using a microservices architecture for scalability and efficient query resolution.

## Language Model Service Description
The Language Model Service integrates with OpenAI's API to generate intelligent responses to user queries. It processes incoming messages and provides AI-generated responses based on the context of the conversation.

## Dependencies
Before running the Language Model Service, ensure the following repository is cloned and its service is running:
- Chat Service: https://github.com/harjeevansingh/genie-chat-service

## Prerequisites
- Docker and Docker Compose installed on your system
- OpenAI API key (requires a paid account at OpenAI)
- Chat Service should be up and running

## Steps to Run
1. Navigate to the Language Model Service directory:
      cd /path/to/genie-language-model-service
2. Create a .env file with the following content:
      OPENAI_API_KEY=your_openai_api_key_here
      (Replace `your_openai_api_key_here` with your actual OpenAI API key.)
3. Start the service:
      docker-compose up
4. Verify that the service is running:
      docker ps

For full deployment of the Genie Chat Bot system, refer to the main deployment guide.
   https://docs.google.com/document/d/1UWd703j5do7ilt7zrmmJodJIY9KydrCAX3lkqXyKH2w/edit

