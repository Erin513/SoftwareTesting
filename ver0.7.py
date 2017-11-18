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

def PrintStar():
	print('*' * 20)
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

def Backupfile(Path, Origin):
	with open(Path + '.backup', 'w', encoding = 'utf-8') as f:
		f.write(Origin)
	return

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


def ReturnFaultInjection(Method):
	if(Method.find('return false') != -1 or Method.find('return true') != -1):
		Method = Method.replace('return false', '###')
		Method = Method.replace('return true', '***')
	else:
		return -1

	Method = Method.replace('###', 'return true')
	Method = Method.replace('***', 'return false')

	return Method

def Writetofile(Path, Injected):
	with open(Path, 'w', encoding = 'utf-8') as f:
		f.write(Injected)
	return 1

def CompareFile(Path1,Path2):
	PrintStar()
	with open(Path1,'r',encoding='utf-8') as f1, open(Path2,'r',encoding='utf-8') as f2:
		for line_no, (line1, line2) in enumerate(zip(f1, f2)):
			if line1 != line2:
				print('line ' + str(line_no) + '\t' + line1.strip() + ' change to ' + line2.strip())
	PrintStar()

if __name__ == '__main__':
	pathprefix = os.getcwd()
	if sys.platform == 'win32':
		pathsuffix = r'\src\main\cn\edu\sjtu\software'
		pathjoin = '\\'
	else:
		pathsuffix = '/src/main/cn/edu/sjtu/software'
		pathjoin = '/'
	path = pathprefix + pathsuffix
	file_name = ListFile(path,'java')
	PrintStar()
	for idx, val in enumerate(file_name,start=1):
		print(idx, val)
	PrintStar()
	selnum1 = int(input("请选择需要的类文件:"))

	path_abs = path+ pathjoin + file_name[selnum1 - 1]
	with open(path_abs,'r',encoding='utf-8') as f:
		classfile = f.read()
	Backupfile(path_abs, classfile)
	tree = javalang.parse.parse(classfile)


	method = []
	for path, node in tree.filter(javalang.tree.MethodDeclaration):
		method.append(node.name)
	PrintStar()
	for idx, val in enumerate(method,start=1):
		print(idx, val,'()')
	PrintStar()

	selnum2 = int(input("请选择需要的方法:"))
	methodpos = SplitMethod(classfile, method[selnum2 - 1]) #选择方法的位置

	methodtxt = classfile[methodpos[0]:methodpos[1]]
	PrintStar()
	print(methodtxt)
	PrintStar()

	selectfault = ['完成注入','Return错误','2','3','4','5']
	for idx, val in enumerate(selectfault):
		print(idx, val,)
	while 1:
		selnum3 = int(input("请选择需要的错误类型:"))
		if selnum3 == 1:
			methodtxt = ReturnFaultInjection(methodtxt)
			PrintStar()
			print(methodtxt)
			PrintStar()
		elif selnum3 == 2:
			pass
		elif selnum3 == 3:
			pass
		elif selnum3 == 4:
			pass
		elif selnum3 == 5:
			pass
		else:
			break

	classfile = classfile[:methodpos[0]] + methodtxt + classfile[methodpos[1]:]
	if(input("需要展示注入后的代码吗？y/n:") == 'y'):
		PrintStar()
		print(classfile)
		PrintStar()

	if(Writetofile(path_abs, classfile) == 1):
		PrintStar()
		print('注入完成！')
		PrintStar()

	CompareFile(path_abs + '.backup', path_abs)