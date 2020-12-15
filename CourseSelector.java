/*
Created by: Malav Jani
Banner id.:B00851408
Subject: Software Development Concept
Assignment 1
*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;


public class CourseSelector {
    //declared static as every method can access the variable
    static List<List> courses = new ArrayList<List>();

    /*This will read the given file and add the file content into courses
    First conditional loop will check if the read method is being called another time or not
    If called multiple time it will flush courses list
    Scanner is used to read the file
    space is trimmed and all the strings are converted to uppercase and added to courses list*/
    int read(String filepath)   {

        String demo = "";
        courses.removeAll(courses);
        try {
            int count = 0;
            Scanner read = new Scanner(new File(filepath));
            //reading file
            while (read.hasNextLine()) {
                demo = read.nextLine();
                if (demo.isEmpty()) {
                    continue;
                } else {
                    count++;
                    String ent = demo.toUpperCase();
                    String[] test = ent.split(" +");//spliting whitespace from string(it could have multiple whitespace)
                    List<String> list = Arrays.asList(test);
                    courses.add(list);

                }

            }
            read.close();
            return count;

        }
        catch (FileNotFoundException e) {
            return -1;
        } catch (Exception e) {
            return -1;
        }

    }

    /*This method will use courses list for printing 2D matrix and will store it into given file path
      in a sorted manner
      In this method one loop will create new list of subjects excluding the repeated
      */
    public boolean showCommonAll(String filename) {
        try {

            //creating list of unique subjects
            List<String> newList = new ArrayList<String>();

            //creating a new list excluding all the redundant courses
            for (int j = 0; j < courses.size(); j++) {
                for (int k = 0; k < courses.get(j).size(); k++) {
                    if (!newList.contains(courses.get(j).get(k))) {
                        String addContent = courses.get(j).get(k).toString();
                        newList.add(addContent);
                    }

                }
            }

            int size = newList.size();//size of new list
            int[][] matrix = new int[size][size];//making 2d matrix of newlist size

            Collections.sort(newList); //sorting the list

            //making 2d matrix
            for (int j = 0; j < size; j++) {

                for (int k = j + 1; k < size; k++) {
                    int count = 0;
                    for (int x = 0; x < courses.size(); x++) {
                        if (courses.get(x).contains(newList.get(j)) && courses.get(x).contains(newList.get(k))) {
                            count++;
                        }
                    }
                    matrix[j][k] = count;
                    matrix[k][j] = count;
                }
            }

            BufferedWriter Write = new BufferedWriter(new FileWriter(filename)); //witer to write data into file

            //loop to write matrix into file
            for (int j = 0; j < size; j++) {
                Write.write(newList.get(j) + "\t");
                for (int y = 0; y < size; y++) {
                    Write.write(matrix[j][y] + "\t");
                }
                Write.newLine();
            }
            Write.close();
            return true;//return true if success
        } catch (FileNotFoundException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /*This method will create a 2d string for given courses and will return true after successful creation
     there is one loop which will create new list of unique subjects and then it will be used in making 2d matrix*/
    public boolean showCommon(String Courses) {
        //return false if course string is empty
        if (Courses.isEmpty()) {
            return false;
        }
        if (courses.isEmpty()) {
            return false;
        }
        try {

            //new course list
            List<String> newList = new ArrayList<String>();
            String[] sub = Courses.split(" +");
            //generating list from given courses
            for (int i = 0; i < sub.length; i++) {
                if (!newList.contains(sub[i])) {
                    newList.add(sub[i].toUpperCase());
                }
            }

            int size = newList.size();//size of new list
            int[][] matrix = new int[size][size];
            //making 2d matrix and also printing the subjects
            for (int j = 0; j < size; j++) {

                for (int k = j + 1; k < size; k++) {
                    int count = 0;
                    for (int x = 0; x < courses.size(); x++) {
                        if (courses.get(x).contains(newList.get(j)) && courses.get(x).contains(newList.get(k))) {
                            count++;
                        }
                    }
                    matrix[j][k] = count;
                    matrix[k][j] = count;
                }
            }
            //printing the matrix
            for (int j = 0; j < size; j++) {
                System.out.print(newList.get(j) + "\t");
                for (int y = 0; y < size; y++) {
                    System.out.print(matrix[j][y] + "\t");

                }
                System.out.println();
            }
            return true;
        } catch (NullPointerException e) {
            return false;
        }

    }

    /*Recommend will return an arraylist according to the arguments
    It will check the support and the recommendation count to return the list
    If the students are less to make the recommendation it will return null
    There is one conditional loop which will check the student count and support if the students are less to make recommendation it will return null
    */
    public ArrayList<String> recommend(String taken, int support, int recommendations) {

        if(courses.isEmpty())
        {
            return null;
        }
        if(recommendations==0)
        {
            return null;
        }
        //lists for taken courses
        List<String> takenCourses = new ArrayList<>();
        //list of courses which are in taken list
        List<String> singleTakenList = new ArrayList<>();
        String takenInput[] = taken.split(" +");

        for (int i = 0; i < takenInput.length; i++) {
            takenInput[i] = takenInput[i].toUpperCase();
        }
        //number of students used to check support
        int studentCount = 0;

        boolean check = true;

        //This loop will add the taken courses having other courses into new list called singletakenlist
        for (int i = 0; i < courses.size(); i++) {
            for (String x :
                    takenInput) {
                if (!courses.get(i).contains(x)) {
                    check = false;
                    break;
                } else {
                    check = true;
                }

            }
            if (check == true) {
                takenCourses.add(courses.get(i).toString());
                for (int x = 0; x < courses.get(i).size(); x++) {
                    singleTakenList.add(courses.get(i).get(x).toString());
                }
                studentCount++;
                check = false;

            }
        }
        //
        if (studentCount >= support)
        {
            Set<String> noRepeat = new HashSet<>();
            // making the list having unique entries by using set
            noRepeat.addAll(singleTakenList);


            List<String> doubleSubRemove = new ArrayList<>();
            for (int i = 0; i < takenInput.length; i++) {
                //making list from input array
                doubleSubRemove.add(takenInput[i]);

            }
            Collections.sort(singleTakenList);

            //making the list of single subjects which are not taken
            for (int x = 0; x < singleTakenList.size(); x++) {
                for (String eliminate :
                        takenInput) {

                    if (singleTakenList.get(x).contains(eliminate)) {
                        singleTakenList.removeAll(doubleSubRemove);
                    }

                }
            }
            Set<String> unique = new HashSet<>();
            unique.addAll(singleTakenList);

            //this loops will count the subjects not taken from the list of taken and also will add the subjects name into list
            List<String> subjectName = new ArrayList<>();
            List<Integer> countSubject = new ArrayList<>();
            for (int i = 0; i < takenCourses.size(); i++) {
                for (String point :
                        unique) {
                    if (takenCourses.get(i).contains(point) && !subjectName.contains(point)) {
                        subjectName.add(point);
                        int x = subjectName.indexOf(point);
                        countSubject.add(x, 1);
                    } else if (takenCourses.get(i).contains(point) && subjectName.contains(point)) {
                        int x = subjectName.indexOf(point);
                        int count = countSubject.get(x);
                        countSubject.set(x, count + 1);
                    }
                }
            }

            //integer list to store the count of subjects
            List<Integer> sortedCount = new ArrayList<>();
            sortedCount.addAll(countSubject);
            Collections.sort(sortedCount);
            Collections.reverse(sortedCount);
            ArrayList<String> recommend = new ArrayList<>();

            //sorting the subjects count wise
            for (int i = 0; i < sortedCount.size(); i++) {
                for (int j = 0; j < countSubject.size(); j++) {
                    if (sortedCount.get(i) == countSubject.get(j)) {
                        if (!recommend.contains(recommend)) {
                            recommend.add(subjectName.get(j));
                        }

                    }
                }

            }

            //final recommend unique list
            ArrayList<String> uniqueRecommend = new ArrayList<>();//list of unique
            for (String x : recommend) {
                if (!uniqueRecommend.contains(x)) {
                    uniqueRecommend.add(x);
                }
            }
            recommend.removeAll(recommend);

            //loop for deciding how many subjects to return
            int cnt = 0;
            if (recommendations == sortedCount.size()) {
                return uniqueRecommend;
            }
            //if the recommendation is more than the subject list than it will all the subjects
            else if(recommendations>countSubject.size())
            {
                return uniqueRecommend;
            }
            else if (recommendations > 1) {
                for (int i = 0; i < sortedCount.size(); i++) {
                    if (sortedCount.get(i) >= sortedCount.get(recommendations)) {
                        cnt = i;
                    }
                }
            } else if (recommendations == 1) {
                for (int i = 0; i < sortedCount.size(); i++) {
                    if (sortedCount.get(i) >= sortedCount.get(recommendations)) {
                        cnt = i;
                    }
                }
            } else if (recommendations <= 0) {
                return null;
            }
            //adding the list to final list for returning the recommendation
            for (int i = 0; i <= cnt; i++) {
                recommend.add(uniqueRecommend.get(i).toString());
            }
            return recommend;
        }
        else {
            return null;
        }

    }
}