#!/usr/bin/env python3
"""

/*******************************************************************************
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License 2.0 which is available at 
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/

@purpose: AndroidBootstrap project renamer
    run, write input/std input for the new project name
@author: Muhammad Ramdan Izzulmakin
@date: 2024-August

github.com/imakin/androidbootstrap
copyright (c) 2024 Muhammad Ramdan Izzulmakin

"""
import os
import fileinput
import sys
import shutil


BASE_DIR = os.path.realpath(sys.argv[0])
if (BASE_DIR.endswith(".py")):#in some python implementation the sys.argv[0] is the folder
    BASE_DIR = os.path.dirname(BASE_DIR)
sys.path.append(BASE_DIR) #allow import files from this path

def replace_in_file(file_path, old_string, new_string):
    try:
        with fileinput.FileInput(file_path, inplace=True) as file:
            for line in file:
                print(line.replace(old_string, new_string), end='')
    except Exception as e:
        # print(e)
        pass

def replace_in_folder(folder_path, old_string, new_string):
    for root, dirs, files in os.walk(folder_path):
        for file in files:
            if file==sys.argv[0]:continue
            file_path = os.path.join(root, file)
            replace_in_file(file_path, old_string, new_string)

def rename_folders(folder_path, old_name, new_name):
    for root, dirs, files in os.walk(folder_path, topdown=False):
        for dir in dirs:
            if old_name in dir:
                old_dir_path = os.path.join(root, dir)
                new_dir_path = os.path.join(root, dir.replace(old_name, new_name))
                if new_dir_path!=old_dir_path:
                    print(f"renaming {old_dir_path} to {new_dir_path}")
                    shutil.move(old_dir_path, new_dir_path)

# Usage

print("renaming project from AndroidBootstrap")
print("input CamelCase project name: example (AndroidKu) ")

new_name = input()
old_name = "AndroidBootstrap"

print(f"working on {BASE_DIR}")

replace_in_folder(BASE_DIR, old_name, new_name)
replace_in_folder(BASE_DIR, old_name.lower(), new_name.lower())

rename_folders(BASE_DIR, old_name, new_name)
rename_folders(BASE_DIR, old_name.lower(), new_name.lower())




