import socket

HOST = "100.118.95.40"
PORT = 5001

server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
server_socket.bind((HOST, PORT))

print("UDP server is waiting for messages...")

while True:
    data, client_address = server_socket.recvfrom(1024)

    client_message = data.decode("utf-8").strip()
    print("Client:", client_message)

    if client_message.lower() == "exit":
        print("Chat ended by client.")
        break

    server_message = input("Server: ")

    server_socket.sendto(
        server_message.encode("utf-8"),
        client_address
    )

    if server_message.lower() == "exit":
        print("Chat ended by server.")
        break

server_socket.close()

print("UDP server closed.")