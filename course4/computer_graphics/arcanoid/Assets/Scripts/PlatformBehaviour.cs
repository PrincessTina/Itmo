using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlatformBehaviour : MonoBehaviour {
    public float speed;
    public float border;
    private Vector3 position;
    private int lives;
    private int score;
    	
    // Инициализация
    void Start() {
        position = transform.position;
        lives = 3;
        score = 0;
    }

    // Перерисовка кадра игры
    void Update() {
        move();
        checkBorders();
    }

    void move() {
        position.x += Input.GetAxis("Horizontal") * speed;
        transform.position = position;
    }

    void checkBorders() {
	position.x = (position.x < -border) ? -border : (position.x > border) ? border : position.x;
	transform.position = position;
    }

    void addScores(int scores) {
        score += scores;
    } 

    void takeLife() {
        lives--;
    }

    void OnGUI() {
        GUI.Label(new Rect(5.0f, 3.0f, 200.0f, 200.0f), "Live's: " + lives + " Score: " + score);
    }
}
