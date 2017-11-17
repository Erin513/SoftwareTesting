#!/usr/bin/python
# -*- coding: utf-8 -*-

import javalang #可以在https://github.com/c2nes/javalang得到
import sys
import os
import re

#筛选java文件
def IsSubString(SubStrList, Str):

	flag = True
	for substr in SubStrList:
		if not(substr in Str):
			flag = False

	return flag

#列出java文件
def ListFile(FindPath, FlagStr):
	FileList = []
	FileNames = os.listdir(FindPath)
	if(len(FileNames)>0):
		for fn in FileNames:
			if(len(FlagStr)>0):
				if(IsSubString(FlagStr, fn)):
					FileList.append(fn)

			else:
				FileList.append(fn)

	if (len(FileList)>0):
		FileList.sort()

	return FileList

#分割方法
def SplitMethod(ClassFile, MethodName):
	fixed = r'public.*'
	pattern = fixed + MethodName + r'\((.*?)\)'
	pos = re.search(pattern, ClassFile)
	input = ClassFile[pos.span()[0]:]
	i = input.find('{')
	stack = ['{']

	while len(stack) > 0:
		i += 1
		if input[i] == '}':
			if stack.pop() == '{':
				pass
			else:
				stack.append('}')
				stack.append('}')
		elif input[i] == '{':
			stack.append('{')
		else:
			pass

	postion = [pos.span()[0], pos.span()[0] + i + 1]
	#print(postion)
	return postion



if __name__ == '__main__':
	pathprefix = os.getcwd()
	pathsuffix = r'\src\main\cn\edu\sjtu\software'
	path = pathprefix + pathsuffix
	file_name = ListFile(path,'java')

	for idx, val in enumerate(file_name,start=1):
		print(idx, val)
	selnum1 = int(input("请选择需要的类文件:"))

	with open(path+ '\\' + file_name[selnum1 - 1],'r',encoding='utf-8') as f:
		classfile = f.read()
	tree = javalang.parse.parse(classfile)


	method = []
	for path, node in tree.filter(javalang.tree.MethodDeclaration):
		method.append(node.name)

	for idx, val in enumerate(method,start=1):
		print(idx, val,'()')

	selnum2 = int(input("请选择需要的方法:"))
	methodpos = SplitMethod(classfile, method[selnum2 - 1]) #选择方法的位置
	print(classfile[methodpos[0]:methodpos[1]])