using System.Collections;
using System.Collections.Generic;
using UnityEngine;

enum BallState {
    Active,
    Passive
}

public class BallBehaviour : MonoBehaviour {
    public GameObject platform;
    public AudioClip hitSound;
    private BallState state;
    private Vector3 position;
    private Vector2 force;
    private Rigidbody2D rigidBody;
    	
    // Инициализация
    void Start() {
        state = BallState.Passive;
        position = transform.position;
        force = new Vector2(100.0f, 300.0f);
        rigidBody = GetComponent<Rigidbody2D>();
    }

    // Перерисовка кадра игры
    void Update() {
        move();
    }

    void OnCollisionEnter2D(Collision2D collision) {
        if (state == BallState.Active) {
            GetComponent<AudioSource>().PlayOneShot(hitSound);
        }
    }

    void move() {
        position = transform.position;

        // Стартуем игру по условию
        if (Input.GetButtonDown("Jump") || checkNotAlone()) {
            if (state == BallState.Passive) {
                state = BallState.Active;
                rigidBody.AddForce(force);
            }
        }

        // Проверяем на способность движения вместе с платформой
        if (state == BallState.Passive && platform != null) {
            position.x = platform.transform.position.x;
            transform.position = position;
        }

        // Проверяем на выход за пределы экрана
        if (state == BallState.Active && position.y < -5.2) {
            if (checkNotAlone()) {
                Destroy(gameObject);
            } else {
                state = BallState.Passive;
                position = new Vector3(platform.transform.position.x, -4.15f, position.z);
                transform.position = position;

                rigidBody.velocity = new Vector2(0, 0);

                platform.SendMessage("takeLife");
            }
        }
    }

    // Проверяет, существуют ли другие шары
    bool checkNotAlone() {
        return GameObject.FindGameObjectsWithTag("Ball").Length > 1;
    }

    GameObject generateBalls() {
        return Instantiate(gameObject, transform.position, transform.rotation);
    }

    void addBalls() {
        for (int i = 0; i < 2; i++) {
            generateBalls();
        }
    }
}
