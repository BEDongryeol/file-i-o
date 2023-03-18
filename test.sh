#!/bin/bash

TIME=1645890819895

function generate_post_data()
{
cat <<EOF
  {
    "projectCode" : 3,
    "agentId" : 91,
    "status" : 200,
    "responseTime" : 6000,
    "time" : "$TIME"
  }
EOF
}


for ((a=0; a<$1; a++))
do
    # shellcheck disable=SC2003
    TIME=$(expr $TIME + 1)
    data="$(generate_post_data)"

    curl -XPOST 'http://localhost:8080/log/request' \
    --header 'Content-Type: application/json' \
    --data-raw "$data"
done
