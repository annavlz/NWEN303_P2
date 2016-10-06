Task 1: EchoServerT1, EchoClientT1, ConnectionT1, SearchT1

In EchoServer changed the path to Connection.
In Connection changed requests and response strings.
In EchoClient changed request strings.
Result: see T1aScreenshot.jpg

In Search: new argument is taken keysNumber and used in for loop.
Result: see T1bScreenshot.jpg

******************************************************************

Task2: EchoServerT2, EchoClientT2, ConnectionT2
Meeting requirements:
1: Client knows only ip and port number, the rest it gets with params
2: Client will send the result of work fo sure: positive or negative, there is no interruptions
3: Client sends "I;m ready" as soon as it's free and sends back results (either positive or negative) when they are ready 
4: Connection is open when there is a request "I;m ready" from a client and closed after receiving any result.
5: The server has a flag on completion, that is used in while loop to keep it open and changed by connection if the result is positive;
if it's not changed the server will shut down when the end of the queue is reached.
