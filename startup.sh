#!/bin/sh

rm -f tpid
server_ip=`/sbin/ifconfig |head -2|tail -1 |awk -F':' '{print $2}'|awk '{print $1}'|awk -F'.' '{print $3"_"$4}'`
DBname="srs"
DBpwd="o2RVyHRl#0t26r-U"
flvpath="/data/slivect/slivect_flv"
mp4path="/data/slivect/video/ctvideo"
logpath="/data/slivect/log/ffmpeglogs/${server_ip}"

nohup /usr/lib/java/bin/java_srs_live_record -jar /opt/app/srs_live_record_service/target/srs_live_record.jar --spring.profiles.active=pro  --spring.datasource.url='jdbc:mysql://livetask.tv189.cn:3306/srs_live_record?characterEncoding=utf-8&allowMultiQueries=true&autoReconnect=true&useSSL=true' --spring.datasource.username=${DBname} --spring.datasource.password=${DBpwd} --liverecord.timeouts=3600 --liverecord.dismantle_concurrent=5  --spring.jpa.show-sql=false  --liverecord.multiple_mount_flvpath=${flvpath}  --liverecord.save_log_path=${logpath} --liverecord.save_video_path=${mp4path}  --liverecord.ffmpeg_path=./  --liverecord.srs_video_failure_time=3  --liverecord.local_video_failure_time=7 --liverecord.recording_delay_time=5  >> logs/startup`date +%Y-%m-%d`.log 2>&1 &
echo $! > tpid
echo "Start Success！"
