#!/bin/sh

class=$1

xml_stream=$(ls /Users/ichigotake/Development/circlebinder/Android-Application/app/src/*/java/$class \
        | xargs grep -oh 'R.layout.[0-9a-zA-Z_]\+' "$class" 2> /dev/null \
        | perl -le 'while(<>){ print (m/R\.layout\.([a-zA-Z0-9_]+)/) }' \
        | xargs -I{} sh -c 'ls /Users/ichigotake/Development/circlebinder/Android-Application/app/src/*/res/layout/{}.xml'
)

for l in $xml_stream
do
    name=$(grep -E '(net.ichigotake)|(circlebinder)' $l | egrep -v tools | perl -le 'while(<>){ print (m/<(.*?)$/) }')
    if [ -n "$name" ] ; then
        echo $name
    fi
    name=$(grep -E '(net.ichigotake)|(circlebinder)' $l | egrep -v tools | perl -le 'while(<>){ print (m/name="(.*?)"/) }')
    if [ -n "$name" ] ; then
        echo $name
    fi
done


