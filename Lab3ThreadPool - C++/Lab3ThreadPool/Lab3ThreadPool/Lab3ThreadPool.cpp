// Lab3ThreadPool.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <iostream>
#include <fstream>
#include <thread>
#include <mutex>
#include <queue>

using namespace std;
const int SIZE_1 = 2, SIZE_2 = 2;

const int nmax = 1e6 + 2; //nr maxim threaduri
thread threadPool[nmax]; //lista de threaduri

queue<pair<int, int>> tasks;
vector<vector<int>> resultMatrixProduct;
vector<vector<int>> matrix1;
vector<vector<int>> matrix2;
mutex mutexTaskQueue;

void InitializeResultList(int n, int m)
{
	for (int i = 0; i < n; i++) {
		vector<int> aux(m, 0);
		resultMatrixProduct.push_back(aux);
	}
}

//creerea listei de taskuri ce urmeaza sa fie facute, un taks reprezinta o pereche <indiceLinie,indiceColoana>
void InitializeTaskQueue(int n, int m)
{
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < m; j++) {
			tasks.push(make_pair(i, j));
		}
	}
}

void InitializeMatrix(vector<vector<int>>& matrix, int value)
{
	for (int i = 0; i < SIZE_1; i++) {
		vector<int> aux;
		matrix.push_back(aux);
		for (int j = 0; j < SIZE_2; j++) {
			matrix[i].push_back(value);
		}
	}
}

void PrintMatrix(vector<vector<int>>& matrix)
{
	for (int i = 0; i < matrix.size(); i++) {
		for (int j = 0; j < matrix[i].size(); j++) {
			cout << matrix[i][j] << " ";
		}
		cout << "\n";
	}
	cout << "\n";
}

void DoMultiplicationForOneElement()
{
	while (true) {
		pair<int, int> currentTask;
		thread::id this_id = this_thread::get_id();
		mutexTaskQueue.lock();

		cout << "Thread id:" << this_id << " " << tasks.size() << "\n";

		if (tasks.empty()) { //daca nu mai e nici un task, threadul a terminat
			mutexTaskQueue.unlock();
			return;
		}
		currentTask = tasks.front(); //altfel ia primul task din lista
		tasks.pop();
		mutexTaskQueue.unlock();
		int line = currentTask.first;
		int column = currentTask.second;
		for (int i = 0; i < matrix1[line].size(); i++) {
			resultMatrixProduct[line][column] += matrix1[line][i] * matrix2[i][column];
		}
	}
}

//Create threads
void createThreadsProd(int n)
{
	for (int i = 0; i < n; i++) {
		threadPool[i] = thread(DoMultiplicationForOneElement);
	}
}

//join threads 
void joinThreadsProd(int n)
{
	for (int i = 0; i < n; i++) {
		threadPool[i].join();
	}
}

int main()
{
	ofstream log("log.txt", ios_base::app | ios_base::out);
	clock_t tStart = clock();
	InitializeMatrix(matrix1, 1);
	InitializeMatrix(matrix2, 2);

	//PrintMatrix(matrix1);
	//PrintMatrix(matrix2);


	InitializeResultList(matrix1.size(), matrix2[0].size());
	InitializeTaskQueue(matrix1.size(), matrix2[0].size());

	int nrThreads = 4;
	createThreadsProd(nrThreads);
	joinThreadsProd(nrThreads);

	//PrintMatrix(resultMatrixProduct);
	log << "NUMBER OF NR THREADS:" << nrThreads;
	log << " TIME TAKEN:" << (double)(clock() - tStart) / CLOCKS_PER_SEC;
	log << "  Matrix size " << SIZE_1 << " & " << SIZE_2 << "\n";
	return 0;
}
