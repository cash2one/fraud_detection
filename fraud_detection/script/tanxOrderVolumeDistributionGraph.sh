hadoop dfs -text /mroutput/dongjiahao/new_basedata/tanx_hour_distribution_2015-11-17/part-r-00000 > /data/dongjiahao/svnProject/basedata/trunk/dataLog/tanxVolumeData.txt
python /data/dongjiahao/svnProject/basedata/trunk/src/com/mr/data_analysis_python/new_tanxOrderVolumeDistributionGraph.py