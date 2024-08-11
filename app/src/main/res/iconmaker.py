"""

/*******************************************************************************
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License 2.0 which is available at 
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/

@purpose: AndroidBootstrap icon maker
    reading ic_launcher.webp and ic_launcher_round.webp in current working directory, then 
    resize them for various dpi in each corressponding folder
@author: Muhammad Ramdan Izzulmakin
@date: 2024-August

github.com/imakin/androidbootstrap
copyright (c) 2024 Muhammad Ramdan Izzulmakin

"""
import os
from PIL import Image

def resize_and_save(image_name, size, output_folder):
    img = Image.open(image_name)
    img = img.resize(size, Image.LANCZOS)
    
    if not os.path.exists(output_folder):
        os.makedirs(output_folder)
    
    output_path = os.path.join(output_folder, image_name)
    img.save(output_path, 'WEBP')
    print(f"Saved {image_name} to {output_folder}")

# Define the sizes for different densities
sizes = {
    'mdpi': (48, 48),
    'hdpi': (72, 72),
    'xhdpi': (96, 96),
    'xxhdpi': (144, 144),
    'xxxhdpi': (192, 192)
}

# List of image names to process
image_names = ['ic_launcher.webp', 'ic_launcher_round.webp']

# Process each image
for image_name in image_names:
    if os.path.exists(image_name):
        for density, size in sizes.items():
            output_folder = f'mipmap-{density}'
            resize_and_save(image_name, size, output_folder)
    else:
        print(f"{image_name} not found in the current directory.")

print("Resizing complete!")
