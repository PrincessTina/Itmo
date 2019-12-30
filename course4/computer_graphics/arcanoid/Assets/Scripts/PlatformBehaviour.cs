using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

// Перечисление состояний размера платформы
enum PlatformSize {
    Standart,
    Big,
    Small
}

// Класс констант
public class Consts {
    public const int maxTimer = 15;
}

public class PlatformBehaviour : MonoBehaviour {
    public AudioClip pointSound;
    public AudioClip lifeSound;    
    public float speed;
    public float border;
    private Vector3 position;
    private PlatformSize size;
    private int lives;
    private int score;
    private string currentSceneName;
    private int timer = Consts.maxTimer;
    	
    // Инициализация
    void Start() {
        position = transform.position;
        size = PlatformSize.Standart;
        lives = 3;
        score = 0;
        currentSceneName = SceneManager.GetActiveScene().name;

        InvokeRepeating("runTimer", 0, 1);
    }

    // Перерисовка кадра игры
    void Update() {
        move();
        checkBorders();
        checkTransition();
    }

    // Управляет таймером в игре
    void runTimer() {
        if (timer == 0) {
            changeSize(PlatformSize.Standart);
        } else if (size != PlatformSize.Standart) {
            timer -= 1;
        }
    }

    // Осуществляет горизонтальное движение платформы
    void move() {
        position.x += Input.GetAxis("Horizontal") * speed;
        transform.position = position;
    }

    // Предотвращает выход за границы экрана
    void checkBorders() {
	position.x = (position.x < -border) ? -border : (position.x > border) ? border : position.x;
	transform.position = position;
    }

    // Проверяет выполнение условий перехода на другой уровень
    void checkTransition() {
        if (lives == 0 || Input.GetKey(KeyCode.Escape)) {
            restart();
        }

        if ((GameObject.FindGameObjectsWithTag("Block")).Length == 0) {
            switch(currentSceneName) {
                case "Level1":
                    loadLevel("Level2");
                    break;
                case "Level2":
                    loadLevel("Level0");
                    break;
                default:
                    quit();
                    break;
            }
        }
    }

    // Добавляет очки
    void addScores(int scores) {
        score += scores;
        GetComponent<AudioSource>().PlayOneShot(pointSound);
    } 

    // Отнимает одну жизнь
    void takeLife() {
        lives--;
        GetComponent<AudioSource>().PlayOneShot(lifeSound);
    }

    // Бонус на добавление одной жизни в случае немаксимального числа жизней в текущий момент
    void addLife() {
        lives = (lives == 3) ? lives : lives + 1;
    } 

    // Бонус на увеличение размера платформы
    void increaseSize() {
        changeSize(PlatformSize.Big);
    } 

    // Бонус на уменьшение размера платформы
    void decreaseSize() {
        changeSize(PlatformSize.Small);
    } 

    // Меняет размер платформы и связанные с ним параметры работы игры
    void changeSize(PlatformSize newSize) {
        Vector3 scale = transform.localScale;
        size = newSize;

        switch(size) {
            case PlatformSize.Big:
                scale.x = 1.1f;
                border = 7.84f;
                break;
            case PlatformSize.Small:
                scale.x = 0.9f;
                border = 8.1f;
                break;
            case PlatformSize.Standart:
                scale.x = 1.0f;
                border = 7.97f;
                break;
        }

        transform.localScale = scale;
        timer = Consts.maxTimer;
    }

    // Загрузка сцены
    void loadLevel(string levelName) {
        SceneManager.LoadScene(levelName);
    }

    // Перезапуск уровня
    void restart() {
        loadLevel(currentSceneName);
    }

    void quit() {
        Application.Quit();
    }

    void OnGUI() {
        string label = "Live's: " + lives + " Score: " + score;

        if (size != PlatformSize.Standart) {
            label += " Timer: " + timer;
        }

        GUI.Label(new Rect(5.0f, 3.0f, 200.0f, 200.0f), label);
    }
}
