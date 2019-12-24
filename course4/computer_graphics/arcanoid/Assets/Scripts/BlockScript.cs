﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public enum BlockType {
    Blue,
    Green,
    Red,
    Yellow
}

public class BlockScript : MonoBehaviour {
    public BlockType type = BlockType.Blue;
    private int maxNumberOfHits;
    private int scores;
    private int numberOfHits;
    	
    // Инициализация
    void Start() {
        numberOfHits = 0;
        setTypeParameters();
    }

    // Перерисовка кадра игры
    void Update() {
        
    }

    void OnCollisionEnter2D(Collision2D collision){
        if (collision.gameObject.tag == "Ball") {
            numberOfHits++;

            if (numberOfHits == maxNumberOfHits) {
                GameObject platform = GameObject.FindGameObjectsWithTag("Platform")[0];
                platform.SendMessage("addScores", scores);
                Destroy(this.gameObject);
            }
        }
    }

   // Устанавливает параметры блока, характерные для его типа
    void setTypeParameters() {
        switch(type) {
            case BlockType.Blue:
                maxNumberOfHits = 1;
                scores = 1;
            break;
            case BlockType.Green:
                maxNumberOfHits = 2;
                scores = 2;
            break;
            case BlockType.Yellow:
                maxNumberOfHits = 3;
                scores = 3;
            break;
            case BlockType.Red:
                maxNumberOfHits = 4;
                scores = 4;
            break;
        }
    }
}
