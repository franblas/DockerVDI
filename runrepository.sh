#!/bin/bash

sudo docker run -d -p 5000:5000 -v /registry:/tmp/registry:rw registry