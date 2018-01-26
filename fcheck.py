#!/usr/bin/python
#-*- encoding: utf-8 -*-

from __future__ import print_function

import sys
import time
import os, datetime, subprocess

#文件修复成功
#define CHECK_OK		0
#文件路径错误
#define PATH_ERROR		1
#文件转码失败
#define TRANSCODE_ERROR	2
#未知错误
#define UNKNOWN_ERROR	9

# reload sys model to enable the getdefaultencoding method.
reload(sys)
# set the default encoding to utf-8
# using exec to set the encoding, to avoid error in IDE.
exec("sys.setdefaultencoding('utf-8')")
assert sys.getdefaultencoding().lower() == "utf-8"

#ffmpeg_path = "/opt/pro/srs/objs/ffmpeg/bin/ffmpeg"
#mp4box_path = "/data/michael/git/gpac/dist/bin/MP4Box"
log_folder = sys.argv[2] #"/data/log/fcheck"
log_filename=sys.argv[3]
# simple log functions.
def trace(msg):
    #log_filename = "fcheck_%s.log" % datetime.datetime.now().strftime("%Y-%m-%d");
    
    fcheck_log_filepath = os.path.join(log_folder, log_filename)
    
    date = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    #print "[%s][trace] %s"%(date, msg)
    
    with open(fcheck_log_filepath, 'a') as f:
        f.write("[%s][trace] %s\n" % (date, msg))

def usage(app_path):
    print("fcheck tool: check media file properties")
    print("usage: %s file_path" % app_path)

def TimeStampToTime(timestamp):
    timeStruct = time.localtime(timestamp)
    return time.strftime('%Y-%m-%d %H:%M:%S', timeStruct)

def repair_video():
    if len(sys.argv) < 4:
        usage(sys.argv[0])
        exit(0)

    trace("===============begin to repair video==================")

    #log_folder=sys.argv[2]
    #log_filename=sys.argv[3]
    dirname = os.path.dirname(sys.argv[0])
    abspath = os.path.abspath(dirname)
    ffmpeg_path = os.path.join(abspath, "ffmpeg")
    mp4box_path = os.path.join(abspath, "MP4Box")

    in_file = sys.argv[1]
    trace("in_file: %s" % in_file)

    in_file_dirname = os.path.dirname(in_file)
    in_abspath = os.path.abspath(in_file_dirname)
    tmp_folder = os.path.join(in_abspath, "tmp")
    if not os.path.exists(tmp_folder):
        os.mkdir(tmp_folder)

    if not os.path.exists(in_file):
        trace("in_file %s NOT exits!" % in_file)
        print('1', end='')
        return
    if not os.path.exists(ffmpeg_path):
        trace("ffmpeg NOT exits!")
        print('1', end='')
        return
    if not os.path.exists(mp4box_path):
        trace("mpbox NOT exits!")
        print('1', end='')
        return
        
    tmp = in_file.split("/")
    file_name = tmp[-1]
    
    date = datetime.datetime.now().strftime("%Y-%m-%d")
    
    # step 1: ffmpeg
    log_filename = "ffmpeg_%s.log" % date;
    ffmpeg_log_filepath = os.path.join(log_folder, log_filename)
    p = subprocess.Popen([ffmpeg_path, "-i", in_file, "-c:v", "copy", "-c:a", "copy",
        "-metadata", "service_provider=tysx", "-metadata", "service_name=tysx_live", "-y", 
        os.path.join(tmp_folder, file_name)],
        stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    p.wait()
    with open(ffmpeg_log_filepath, "a") as f:
        f.write("=================================\n")
        
        lines = p.stderr.readlines()
        for line in lines:
            f.write("%s\n" % line.strip("\n"))

    if not p.returncode == 0:
        trace("ffmpeg failed to convert media file: %s" % in_file)
        print('2', end='')
        return;
        
    # step2: mp4box
    log_filename = "mp4box_%s.log" % date;
    mp4box_log_filepath = os.path.join(log_folder, log_filename)
    p = subprocess.Popen([mp4box_path, "-isma", "-noprog", os.path.join(tmp_folder, file_name)], 
        stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    p.wait()
    with open(mp4box_log_filepath, "a") as f:
        f.write("=================================\n")
        
        lines = p.stderr.readlines()
        for line in lines:
            f.write("%s\n" % line.strip("\n"))

    if not p.returncode == 0:
        trace("mp4box failed to repair media file: %s" % in_file)
        print('2', end='')
        return;
        
    # step3: mv file
    origin_size = os.path.getsize(in_file)
    origin_mtime = os.path.getmtime(in_file)

    os.rename(os.path.join(tmp_folder, file_name), in_file)
    
    new_size = os.path.getsize(in_file)
    new_mtime = os.path.getmtime(in_file)

    trace("size %d -> %d" % (origin_size, new_size))
    trace("mtime %s -> %s" % (TimeStampToTime(origin_mtime), TimeStampToTime(new_mtime)))

    if origin_size == new_size and origin_mtime == new_mtime:
        print('9', end='')
        return

    print('0', end='')
if __name__ == '__main__':
    repair_video()







