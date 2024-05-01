FROM node:14

# Create app directory
RUN mkdir -p /app
WORKDIR /app

# Install app dependencies
COPY package*.json ./
RUN npm install

# Bundle app source
COPY . .

# Build the app
RUN npm run build

EXPOSE 3030
CMD [ "npm", "start", "--", "--port=3030" ]