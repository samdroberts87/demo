# Stage 1: Build React app
FROM node:20-alpine AS build
WORKDIR /app

# Hard-code the environment variable
ENV VITE_API_URL=/api

# Copy package.json and package-lock.json first (for caching)
COPY package*.json ./

# Install dependencies
RUN npm ci

# Copy app source
COPY . .

# Build the app
RUN npm run build

# Stage 2: Serve with nginx
FROM nginx:stable-alpine

# Copy build output to nginx public directory
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80

# Healthcheck to ensure endpoint is available else exit with code 1
HEALTHCHECK CMD curl --fail http://localhost:80/ || exit 1

CMD ["nginx", "-g", "daemon off;"] 