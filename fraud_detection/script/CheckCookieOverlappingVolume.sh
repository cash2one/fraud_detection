#!/usr/bin/env bash

ONE_DAY_AGO=`date +%Y-%m-%d --date="-3 day"`
TWO_DAY_AGO=`date +%Y-%m-%d --date="-4 day"`
echo ONE_DAY_AGO=$ONE_DAY_AGO
jar_dir=/data/dongjiahao/svnProject/fraudDetection/trunk/target
jar_name=hadoop_test-1.0-jar-with-dependencies.jar

class_name=com.mr.data_analysis_java.CheckCookieOverlappingVolume

output_path=/mroutput/dongjiahao/new_basedata/CheckCookieOverlappingVolume/log_date=${ONE_DAY_AGO}
bid_input1=/user/ads/log/original_bid/log_date=${ONE_DAY_AGO}
bid_input2=/user/ads/log/original_bid/log_date=${TWO_DAY_AGO}

hadoop dfs -rmr -skipTrash $output_path
hadoop jar ${jar_dir}/$jar_name $class_name $output_path $bid_input1 $bid_input2