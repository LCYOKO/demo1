# syntax=docker/dockerfile:1
# 最小层级数：1.多条 RUN 命令可通过连接符连接成一条指令集以减少层数；2.通过多段构建减少镜像层数
# Step 1: build golang binary
FROM golang:1.17 as builder
WORKDIR /opt/app
COPY go.* ./
RUN go mod download
COPY . .
RUN go build -o example

# Step 2: copy binary from step1
FROM alpine
WORKDIR /opt/app
COPY --from=builder /opt/app/example ./example
CMD ["/opt/app/example"]