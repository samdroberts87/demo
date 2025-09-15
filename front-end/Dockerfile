# Stage 1: Build React app
FROM node:20-alpine AS deps
WORKDIR /app

# Hard-code environment variable
ENV VITE_API_URL=/api

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies (cache layer)
RUN npm ci

# Stage 2: Copy app source and build
FROM node:20-alpine AS build
WORKDIR /app

# Copy deps from previous stage
COPY --from=deps /app/node_modules ./node_modules
COPY . .

# Build the app
RUN npm run build

# Stage 3: Serve with nginx
FROM nginx:stable-alpine
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80

# Healthcheck
HEALTHCHECK CMD curl --fail http://localhost:80/ || exit 1

CMD ["nginx", "-g", "daemon off;"]
