export DOCKER_HOST_IP=$(curl --retry 3 --connect-timeout 2 --max-time 2  -s 169.254.169.254/latest/meta-data/public-ipv4)

echo $DOCKER_HOST_IP > /var/log/dockerIp.log

exec "$@"