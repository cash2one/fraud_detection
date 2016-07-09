
#昨天的日期
ONE_DAY_AGO=`date +%Y-%m-%d --date="-1 day"`
echo ONE_DAY_AGO=$ONE_DAY_AGO

jar_dir=/data/dongjiahao/svnProject/basedata/trunk/target
jar_name=hadoop_test-1.0-jar-with-dependencies.jar

class_name=com.mr.merge_data.BenzLandingPageInfoMR

benz_camp_path=/share_data/benz_camp_log/log_date=${ONE_DAY_AGO}
output_path=/share_data/benz_camp_timestamp/log_date=${ONE_DAY_AGO}


hadoop dfs -rmr -skipTrash $output_path
hadoop jar ${jar_dir}/$jar_name $class_name $output_path $benz_camp_path